import spidev
                                                                                                                                                                                                       
import datetime
import RPi.GPIO as GPIO
import Adafruit_GPIO.SPI as SPI
import Adafruit_SSD1306
import time ,datetime             
import RPi.GPIO as GPIO
from threading import Thread
from PIL import Image
from PIL import ImageDraw
from PIL import ImageFont

from socket import *
from select import select
import sys

import subprocess



spi = spidev.SpiDev()
spi.open(0,0)
spi.max_speed_hz = 100000




######## glober ############

cnt_time=0

first_time=0
pa_sw=0
stop_sw=0
real_time=0
count = 0
RealTime = 0

sub_time =0
test_time =0
pa_time =0 
####### 핀 번호창 #######
TRIG = 23 
ECHO = 24
BUTT_START = 19
BUTT_STOP = 26
SENSER_ON = 21
SENSER_OFF = 20

led_A = 21
led_B = 20
led_C = 16

########  설정창 ######
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

GPIO.setup(TRIG,GPIO.OUT)
GPIO.setup(ECHO,GPIO.IN)

GPIO.setup(BUTT_START,GPIO.IN)
GPIO.setup(BUTT_STOP,GPIO.IN)

GPIO.setup(SENSER_ON,GPIO.OUT)
GPIO.setup(SENSER_OFF,GPIO.OUT)

GPIO.setup(led_A,GPIO.OUT)
GPIO.setup(led_B,GPIO.OUT)
GPIO.setup(led_C,GPIO.OUT)

GPIO.output(led_A,0)
GPIO.output(led_B,0)
GPIO.output(led_C,0)


########buzer############

def buzer():
    
    global sub_time
    sub_time +=5
    GPIO.setmode(GPIO.BCM)
    GPIO.setwarnings(False)
    GPIO.setup(4,GPIO.OUT)
    p = GPIO.PWM(4,100)
    
    speed = 0.5

    p.start(10)
    while True:
        
        try:
            for i in range(0,5):
                p.ChangeFrequency(452)
                time.sleep(0.5)
                p.ChangeFrequency(493)
                time.sleep(0.5)
            break
        except Keyboardinterrupt:  
            pass
        p.stop()
#############oled  ##########
def oled():

    global now_time
    global pa_sw
    global stop_sw
    global cnt_time
    global sub_time
    global test_time 


    RST = None
    Dc = 23
    SPI_PORT =0
    SPI_DEVICE = 0


    disp = Adafruit_SSD1306.SSD1306_128_32(rst=None, i2c_address=0x3C)
    disp.begin()
    disp.clear()
    disp.display()
    width = disp.width
    height = disp.height
    image = Image.new('1',(width,height))
    draw = ImageDraw.Draw(image)

    draw.rectangle((0,0,width,height), outline=0,fill=0)
    padding = -2
    top = padding
    bottom = height-padding

    x = 0
    font = ImageFont.load_default()
    font2 = ImageFont.truetype('digital-7.mono.ttf', 28)
    dateString = '%A %d %B %Y'

    try:


        while True:
            now_time = time.time()
            cnt_time = now_time - first_time -sub_time

            m = (cnt_time/60)/10
            mm = (cnt_time/60)%10
            h = (m/60)/10
            hh =(h/60)%10

            strDate = datetime.datetime.now().strftime(dateString)

            draw.rectangle((0,0,width,height), outline=0, fill=0)
            draw.text((x,top),strDate, font=font,fill=255)
            draw.line((0, top+12, 127, top+12), fill=100)
            draw.text((x+20,top+14),'{0:0.0f}'.format(int(h)), font=font2,fill=255)
            draw.text((x+35,top+14),'{0:0.0f} '.format(int(hh)), font=font2,fill=255)
            draw.text((x+50,top+14),':', font=font2,fill=255)
            draw.text((x+65,top+14),'{0:0.0f}'.format(int(m)), font=font2,fill=255)
            draw.text((x+80,top+14),'{0:0.0f}'.format(int(mm)), font=font2,fill=255)
            disp.image(image)
            disp.display()
            time.sleep(0.1)
            if stop_sw==1:
                print("oled  end")
                break
            if pa_sw==1:
                print("oled pause")
                while True:
                    cnt_time = now_time - first_time -sub_time
                    if pa_sw==2:
                        break;
    except KeyboardInterrupt:
        print("oled stop")
        GPIO.cleanup()



###########ultira_sensor##################


def ultra_sensor_on():

    GPIO.output(TRIG,False)           
    print("waiting for sensor tho settle")   
    global stop_sw
    global pa_sw
    global count 
    global sub_time
    global cnt_time
    global pa_time

    count_err = 0
    count_exception =0

    try:                               
        while True:                    
            now_time = time.time()
            GPIO.output(TRIG,True)     
            time.sleep(0.0001)
            GPIO.output(TRIG,False) 
            count_err = 0
            while GPIO.input(ECHO) == 0 and count_err < 500:
                start = time.time() 
                count_err += 1

            while GPIO.input(ECHO) == 1 and count_err < 500:
                stop = time.time()         

            check_time = stop - start     
            distance = check_time*34300 / 2  
            print("%.1f"%distance)          
            time.sleep(1)

            if distance > 83 or distance <0:    
              count += 1
              print(count)
              if count ==1:
                  pa_time = time.time()
              print("sub time :%d"%sub_time)
              if count > 30:
                  pa_time = time.time() - pa_time
                  sub_time += pa_time
                  cnt_time = now_time - first_time -sub_time
                  print(cnt_time)
                  print("over rest")
                  buzer()
                  time.sleep(0.5)
                  stop_sw=1
            else :
                
                count_exception +=1
                if count_exception == 10:
                    count = 0
                    count_exception =0
                    print("reset")

            if stop_sw==1:
                print("sensor end")
                break
            if pa_sw==1:
                    print("sensor pause")
                    while True:
                        if pa_sw==2:
                            break;
            else :
                count_sensor_error =0
    except KeyboardInterrupt:
        print("ultra sensor stop")
        GPIO.cleanup()



#############stop##########

def stop_sensor():

    time.sleep(1)
    global stop_sw
    #여기에서 전체값 보내기####
    while True:
        if stop_sw == 1:
            break
        if GPIO.input(26) == GPIO.HIGH:
            stop_sw=1
            break

###########pause############
def pause():

    global sub_time
    global pa_sw
    global stop_sw
    sub =0
    time.sleep(1)

    while True:
        if stop_sw==1:
            break

        if GPIO.input(19) == GPIO.HIGH:
            pa_sw+=1
            time.sleep(0.3)
        if pa_sw==1:
            sub = time.time() 
            while True:
                if GPIO.input(19) == GPIO.HIGH:
                    pa_sw+=1
                    if pa_sw==2:
                        sub_time += time.time() - sub
                        print(sub_time)
                        time.sleep(1)
                        pa_sw=0
                        break

####### TCP TONGSIN #########
def tcp(ttt):
    HOST = '192.168.0.44'
    PORT = 8888
    BUFSIZE = 1024
    print(ttt)

    clientSocket = socket(AF_INET, SOCK_STREAM)

    try:

        clientSocket.connect((HOST,PORT))
        clientSocket.sendall(bytes("1/%0.1f \n"%ttt, 'UTF-8'))
        print('Send : Hello, Server!\n')
        data = clientSocket.recv(1024)
        print(data.decode())
        clientSocket.close()

    except Exception as e:

        print(e)
        #clientSocket.close()


########LED###########

def light():

    global pa_sw
    global stop_sw

    while True:
        
        try:
            r = spi.xfer2([1,(8) << 4,0])
            reading = ((r[1]&3) << 8) + r[2]
            voltage = reading * 3.3 / 1024
            if (reading > 350):
                GPIO.output(led_A,1)
                GPIO.output(led_B,0)
                GPIO.output(led_C,0)
                
            if ( reading <= 350 and reading > 100 ):
                GPIO.output(led_A,1)
                GPIO.output(led_B,1)
                GPIO.output(led_C,0)
                
            if ( reading <= 100):
                GPIO.output(led_A,1)
                GPIO.output(led_B,1)
                GPIO.output(led_C,1)
            
            
            if stop_sw==1:
                print("light end")
                break
            
            if pa_sw==1:
                print("light pause")
                while True:
                    if pa_sw==2:
                        break
                time.sleep(0.0001)
        except Exception as e:
            print(e)


#####버튼을 누르면 센서작동###

ultra_on = Thread(target=ultra_sensor_on,args=())
oledsensor = Thread(target=oled,args=())
stop  = Thread(target=stop_sensor,args=())
pause_sensor = Thread(target=pause, args=())
LED  = Thread(target=light, args=())

print("Distance measurement in progress")
while True:
    if GPIO.input(26) == GPIO.HIGH:    
        swich =1                      
        print("start")   
        time.sleep(0.0001)
        break
while True:
    if swich == 1 :
        first_time = time.time()

        oledsensor.start()
        ultra_on.start()
        stop.start() 
        pause_sensor.start()
        LED.start()

        ultra_on.join()       
        oledsensor.join() 
        stop.join() 
        pause_sensor.join()
        LED.join()

        if stop_sw==1:
            if cnt_time < 0:
                cnt_time=0
            print((cnt_time))
            #print((sub_time))
            #print((cnt_time-sub_time))

            
            tcp((cnt_time))
            break

