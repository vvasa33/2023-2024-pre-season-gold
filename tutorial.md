### Tutorial

Our team has used a mix of the FtcRobotController along with the Android Studio Platform in order
to build our opModes for competition. Please don't use blocks or onBotJava, they're not very good
and they make the team look bad.

## How it works:

Let's start from the top. Make sure your Control Hub and Driver Hub are both updated to the latest 
firmware and that you have downloaded the FtcRobotController app and loaded it into Android Studio.

Your code will go into the TeamCode folder, just go through the file hierarchy until you get to the 
readme.md file and make a Java file in that. 

## Start

You can check out our code, but you'll need some experience with the Java programming language in
order to understand what's going on. We've adopted a subsystem hierarchy to support our code. That 
means that if you want to see something related to the claw, for instance, you would go to the claw
class. 

### Components

## The Robot Class

The robot class is where all of the subsystems are initialized and defined. Usually, when developing
an op mode, you don't want to have to copy and paste all of the initialization stuff every time,
so initializing it all in one file makes writing new programs so much easier.

## Hardware Constants

This is where all of the system-wide variables will be defined. For example, if you want your
lift to know when the claw has just finished depositing, here are the steps:

1. Write some code in the subsystem file to get it to deposit the pixels
2. Create an enumeration (reference the java docs if you don't know what this is) in the HardwareConstants.java file and include all of the states you want your claw to be in
3. Make the claw deposit using your new enumeration
4. You're done! Now, your lift can reference the HardwareConstants.java file in its code

When implementing this, make sure that your file hierarchy works and that every file can reference
to HardwareConstants when it needs to.

