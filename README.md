Table of Contents
What is TaskCore?
Why did you name it TaskCore?
Why?
Demo
How to contribute?
How it works?
Managing Tasks
Managing Categories
Clear Database
Configuration
CommandCore Framework
Installation
Want to build locally?
Linux
Windows 10
MacOS
What is TaskCore?
It is a simple cross-platform ToDo management application for those who live inside a terminal where you don't have a fancy GUI.

This application works almost on any platform which includes Linux Distros, MacOS, and Windows. It does not require any runtime to be installed on the target machine, and it is only one single executable that can be copied anywhere.

Why did you name it TaskCore?
"Task" simply represents what this application is helping people with. "Core" represents its simplicity and how it works on a terminal environment.

Why?
Why not? Not everyone likes fancy GUIs or works in X Window environments or prefer leaving their simple looking terminal environment. This application is not for everyone. You should have a special taste for the command line environments.

Demo
Demo

How to contribute?
I am adding the new features I am planning to add here: https://github.com/tarikguney/taskcore/projects/1. You can grab one of the not active one, and let me know if you are working on them. You can also be a beta tester and create issues here if you see any problem: https://github.com/tarikguney/taskcore/issues. Thanks for considering to contribute to the project.

How it works?
Managing Tasks
Add a new task item with priority 1 (highest) and due date:

taskcore add -n "Finish up Task Core" -d 10/11/2020 -p 1
Complete a task item - Passing multiple Ids is allowed:

taskcore c -i 3 2 4
List all active task items:

taskcore ls
List all task items including completed ones:

taskcore ls -c
List tasks under a given category:

taskcore ls -ca Project
Remove a task item - Passing multiple Ids is allowed:

taskcore rm -i 3 2 3
Managing Categories
Add a new category:

taskcore addc -n "Work"
Add a task item to a category:

taskcore add -t "Check if the command works" -d 01/01/2010 -c Work
Remove a category:

taskcore rmc -n "Work"
Clear Database
Delete the database folder, --force is required:

taskcore clear --force
Configuration
TaskCore supports the following environment variables:

Variable	Description
TASKCORE_DB_PATH	Set the DB path
CommandCore Framework
This application is using another project I developed named "CommandCore" as its underlying framework for command parsing and project layout management. CommandCore is an opinionated MVC-based framework for command line application development. You can find more information at CommandCore Github Page.

Installation
You can download an executable for your system: here

Alternatively, taskcore is available in the following package managers:

Arch Linux AUR
(build from source)	yay -S taskcore
or
git clone https://aur.archlinux.org/taskcore.git
cd taskcore
makepkg -si
Want to build locally?
Run the folowing command for the desired operating system. The final executable will be self-contained and single executable file. You don't need to have .NET Core runtime in the target computer where you are planning to run the published binary.

Linux
dotnet publish -c Release --self-contained true --runtime linux-x64 -p:PublishSingleFile=true -p:PublishTrimmed=true ./TaskCore.App.csproj
Windows 10
dotnet publish -c Release --self-contained true --runtime win10-x64 -p:PublishSingleFile=true -p:PublishTrimmed=true .\TaskCore.App.csproj
MacOS
dotnet publish -c Release --self-contained true --runtime osx-x64 -p:PublishSingleFile=true -p:PublishTrimmed=true ./TaskCore.App.csproj
Then, go to in/Release/netcoreapp3.1/osx-x64/publish to locate outputted executable named taskcore and run the taskcore using the subcommands above.
