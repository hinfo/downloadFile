#!/usr/bin/env python
#*-* encoding: UTF-8 *-*

# ****************************
#   Download java for Linux
#     Ver 1.0 By Hinfo
#*****************************

import urllib2, urllib


# Site contendo os arquivos para Download
url = 'http://www.java.com/inc/BrowserRedirect1.jsp?locale=pt_BR'

# A seguir é acessado o site e realizado uma leitura 
site = urllib2.urlopen(url)
html = site.readlines()

print
print """ *****************************************************
	              DOWNLOAD JAVA FOR LINUX
	                  ver 1.0 by Hinfo
	  *****************************************************
"""
print("""Escolha uma das opções:
	1 - Linux RPM (Red Hat, Fedora e derivados)
	2 - Linux i386(586/686) (Arquivo tar | Versão para extração e instalação no braço)
	3 - Linux 64 RPM (Red Hat, Fedora e derivados 64 bits)
	4 - Linux 64 (Arquivo tar 64 bits| Versão para extração e instalação no braço)
	5 - Não tem versão para Debian/Ubuntu?
	""" )

# Espera-se que escolha uma das alternativas.
# Versão 2.0 terá validade de entrada, vá que alguém não saiba ler, :)

choice = int(raw_input('Versão: '))

debian = """
	Digite no terminal como root:
		# add-apt-repository ppa:webupd8team/java
		# apt-get update
		# apt-get install oracle-java8-installer
		# update-java-alternatives -s java-8-oracle 
		# apt-get install oracle-java8-set-default (Configura variaveis de Ambiente)
	"""

#Define a versão a ser instalada
if choice == 1:
	file = "java-linux-i586.rpm"
	version = "109697"
elif choice == 2:
	file = "java-linux-i586.tar.gz"
	version = "109698"
elif choice == 3:
	file = "java-linux-64.rpm"
	version = "109699"
elif choice == 4:
	file = "java-linux-64.tar.gz"
	version = "109700"
elif choice == 5:
	# Para os derivados de Debian/Ubuntu
	print "Ok você tem instalado o Debian/ubuntu!" 
	print debian

else:
	print "Nenhuma de suas escolhas satifaz o menu!"


#Versão escolhida, hands on.
cont = 0
if choice > 0 and choice < 5:
	for line in html:
		if version in line:
			cont = cont + 1
	print "Encontrado %s ocorrência(s)" %cont
	url2 = "http://javadl.sun.com/webapps/download/AutoDL?BundleId="+version
	print "Baixando arquivo: "+file+" ...."
	download = urllib.urlretrieve(url2, file)  
	print "Download Concluído"

else: 
	print "Abandonando..."
	
print ".:.       \m/       .:."

#Finish .:.