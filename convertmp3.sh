#!/usr/bin/env sh
echo "==================================================="
echo "|       Script para converter m4a para mp3 '\m/'! |"
echo "|       GH0ST 34GL3 -*- hinfo@gmx.com             |"
echo "==================================================="

# Remove os espacos em branco do nome dos arquivos.
ls *.m4a | while read line;
do
	FILE_A=`echo $line | tr '[:blank:]' '_'`;
	mv "$line" "$FILE_A";
done
	
# Procura os arquivos m4a, remove a extensao, converte
for a in `find . -iname "*.m4a"`;
do
	#remove a extensao do arquivo e salva na variavel "a"
	FILES=`echo $a | sed s/.m4a//g`;
	#converte o arquivo para mp3
	echo "Convertendo o arquivo $a ...";	
	ffmpeg -i $a -b 320k $FILES.mp3 &> /dev/null;
	echo "OK";
	#muda permissao
	chmod a+x $FILES.mp3;
	#mostra os arquivos convertidos
	ls  $FILES.mp3;
	#unset $FILES;
done
#remove os arquivos m4a
find . -iname "*.m4a" -exec rm -f {} \;
echo -e "\nPronto! :)"


