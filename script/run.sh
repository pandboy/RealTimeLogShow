#!/bin/sh
#ip param1
#password param2
#type    param3
echo $1
echo $2
echo $3
echo "-------start run publish----"
python /home/pandboy/script/publish.py $1 $2 $3
echo "-------run publish.py finish-----"
