import Adafruit_DHT
import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)
GPIO.setup(3, GPIO.IN)

humidity, temp  = Adafruit_DHT.read_retry(Adafruit_DHT.DHT11, 3)

print("Temperature :", temp)
print("Humidity :", humidity)
