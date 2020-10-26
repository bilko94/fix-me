# fix-me
Fix-me is a java program we coded in uni. It simulates a micro stock market with brokers, markets and a router. All messages go through the router for communication.

We created a simulator to automatically run transactions.

# Usage
clone

cd into root repo

sudo apt install maven

mvn clean package

start the router:
java -cp ./target/fixme.jar Router.main

start a broker:
java -cp ./target/fixme.jar Broker.main

start a market:
java -cp ./target/fixme.jar Market.main

start a simulation (router has to be running to simulate):
java -cp ./target/fixme.jar Simulator.apexSimulator
