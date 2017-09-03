# Volt

## Inspiration
The Inspiration for the project idea was the lack of proper automated management systems in the market in the affordable price bracket.

## What it does
Volt is a smart home automation and grid manageent system. The primary goals of the platform are :-

1. To provide affordable home automation systems to each and every household with minimal modification to the house circuitry.

2. To apply smarter loadshedding and distribution of electrical power by monitoring and analysing the power consumption data of the people.

3. A central dashboard for the grid managers allows him to turn off only the heavy duty appliances like A.C.'s, etc. during the power-cuts while leaving the essentials like lighting still on.

4. To provide a pre-paid credit system for the payment of the electrical bills.

## How we built it
We used a raspberry pi for the atomation device to which we connected the normal appliances
The Rpi subscribed to a pubnub channel for receiving updates from the server, app and webpannel
This gave us realtime data of electiricity which enabld us to prepaid electricity and other realtime features

## Challenges we ran into
Controlling the appliances from Rpi
Integrating mulltiple systems on to a single channel for spontaneous response across network

## Accomplishments that we're proud of
Our product is already in the beta phase just out of th hackathon. It is easily scalable and implentable at a much lower cost than the current home automation providers.

## What's next for Volt
Large scale implementation. We hope to pitch the idea to power distributors like TATA Power-DDL (in Delhi) and try to implement the solutions.
