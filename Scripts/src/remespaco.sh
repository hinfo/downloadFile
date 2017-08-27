#!/usr/bin/env sh
# Remove "_" dos nomes.

ls *.* | while read line;
do 
	FILE_A=`echo $line | tr '_' ' ' `;
	mv "$line" "$FILE_A";
done
echo "Terminou."

