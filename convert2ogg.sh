#!/usr/bin/env sh
echo "==================================================="
echo "|       Script para converter m4a para mp3 '\m/'! |"
echo "|       GH0ST 34GL3 -*- hinfo@gmx.com             |"
echo "==================================================="

# Remove os espacos em branco do nome dos arquivos.
ls *.mp3 | while read line;
do
	FILE_A=`echo $line | tr '[:blank:]' '_'`;
	mv "$line" "$FILE_A";
done
	
# Procura os arquivos m4a, remove a extensao, converte
for a in `find . -iname "*.mp3"`;
do
	#remove a extensao do arquivo e salva na variavel "a"
	FILES=`echo $a | sed s/.mp3//g`;
	#converte o arquivo para mp3
	echo "Convertendo o arquivo $a ...";	
	ffmpeg -i $a -b 320k $FILES.ogg &> /dev/null;
	echo "OK";
	#muda permissao
	chmod a+x $FILES.ogg;
	#mostra os arquivos convertidos
	ls  $FILES.ogg;
	#unset $FILES;
done
#remove os arquivos m4a
find . -iname "*.mp3" -exec rm -f {} \;
echo -e "\nPronto! :)"


