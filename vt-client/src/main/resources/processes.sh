#!/bin/bash
main_pid=$1
desc=`pstree -p $main_pid | grep -o '([0-9]\+)' | grep -o '[0-9]\+'`
while read -r pid; do 
    path=`ls -l /proc/$pid/exe | awk '{print $NF}'`
    echo $pid $path
done <<< "$desc"
procs=`ps -ef`
curr=$main_pid
while [ $curr != '1' ]; do
   par_pid=`sudo ps -e -o pid,ppid | grep $curr' ' | awk '{print $NF}'`
   par_path=`sudo ls -l /proc/$par_pid/exe | awk '{print $NF}'`
   echo $par_pid $par_path
   curr=$par_pid
done

