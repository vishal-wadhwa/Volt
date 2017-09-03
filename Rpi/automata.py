import sys
from pubnub import Pubnub
import RPi.GPIO as GPIO
import time
GPIO.setmode (GPIO.BCM)
LIGHT = 23
GPIO.setup(LIGHT,GPIO.OUT)

pubnub = Pubnub(publish_key='pub-c-89e2ce34-7839-47ba-b9b7-3dbc30374038', subscribe_key='sub-c-79a67ba4-4d07-11e7-a368-0619f8945a4f')

def _callback(m, channel):
  if m['led'] == 0:
    GPIO.output(LIGHT,False)
    print 0
  elif m['led'] == 1:
    GPIO.output(LIGHT,True)
    print 1
def _error(m):
	print(m)

pubnub.subscribe(channels='disco', callback=_callback, error=_error)

