#!/usr/bin/env python
#*-* encoding: UTF-8 *-*

# ****************************
#   Download java for Linux
#     Ver 1.1 By Hinfo
#*****************************

import urllib2, urllib, os, re
from subprocess import call



VERSION = "1.1"

# Site contendo os arquivos para Download
url = 'http://www.java.com/inc/BrowserRedirect1.jsp?locale=pt_BR'

# A seguir é acessado o site e realizado uma leitura 
'''try :
	site = urllib2.urlopen(url)
	html = site.readlines()
except: 
	print "Não foi possível acessar o site"
	os._exit()
'''
#======================================================================================
#Funções auxiliares
#======================================================================================

#Busca a versão do java
#
def java():
	url = 'http://www.java.com/pt_BR/download/manual.jsp' 
	site = urllib2.urlopen(url)
	html = site.readlines()
	for line in html:
		if "Version" in line:
			m = re.sub("<.*?>","",line)
			m = m.strip()
			m = re.split('[a-z]+', m, flags=re.IGNORECASE)
			versao = str(m[2]).strip()
			update = str(m[3]).strip()
	return versao, update


#link = "http://javadl.oracle.com/webapps/download/AutoDL?BundleId"

def downloadPlataform(plataforma):
	if plataforma == "x64rpm":
		link = "Linux x64-rpm pt JRE"
		dw = buscaLinkDownload(link)
	elif plataforma == "x64":
		link = "Linux x64 pt JRE"
		dw = buscaLinkDownload(link)
	elif plataforma == "tar":
		link = "Linux pt JRE"
		#tar = buscaLinkDownload(link)
		dw = buscaLinkDownload(link)
	elif plataforma == "rpm":
		link = "Linux rpm pt JRE"
		dw = buscaLinkDownload(link)
	return dw
	
def buscaLinkDownload(link):
	site = urllib2.urlopen(url)
	html = site.readlines()
	for line in html:
		if link in line:
			result = line
			break
	ini = result.find("BundleId")
	fim = result.find("onclick")
	result = result[ini+9:fim-2]
	return result

#tar = downloadPlataform("tar")
#rpm = downloadPlataform("rpm")

#print tar
#print rpm

#=======================================================
# INSTALADORES
#=======================================================
def install_debian():
		'''
		Instala java em deriados do Debian
		'''
		print "Adicionando o ppa:webupd8team/java"
		#os.system('sudo add-apt-repository ppa:webupd8team/java')
		call(["sudo","add-apt-repository","ppa:webupd8team/java"])		
		print "Atualizando os pacotes"
		call(["sudo","apt-get","update"])
		print "Instalando o Java"
		call(["sudo","apt-get" ,"install", "oracle-java8-installer"])
		os.system('sudo update-java-alternatives -s java-8-oracle')
		os.system('sudo apt-get install oracle-java8-set-default')
		os.system('java -version')
	
def install_rpm(f_rpm):
		'''
		Instala no modo rpm
		'''
		print "Instalando o Java..."
		os.system('sudo rpm -ivh %s' % f_rpm)
		os.system('sudo rm %s' % f_rpm)
		
def install_tar(f_tar):
		'''
		Instalar usando arquivo tar.gz
		'''
		print "Instalando o Java..."
		versao = java()[0]
		update = java()[1]
		dirtmp = "jre1.%s.0_%s" % (versao,update)
		subprocess.call(["chmod","+x","%s" % f_tar])
		if os.path.exists("/opt/java") == False:
			os.makedirs("/opt/java")
		
		print "movendo o arquivo %s" % f_tar		
		call(["mv","%s" % f_tar,"/opt/java/%s.tar.gz" % dirtmp])		
		os.chdir("/opt/java")
		
		subprocess.call(["tar","xavf","%s.tar.gz" % dirtmp])
		#subprocess.call(["mv","%s" % dirtmp,"/usr/lib/java/"])
		os.remove("%s.tar.gz" % dirtmp)		
		#call(["rm","%s.tar.gz" % dirtmp])
		#f = open("/etc/environment", "r+")
		f = open("/etc/bash.bashrc","r+")		
		f.readlines()
		f.write("JAVA_HOME=/opt/java/%s/bin\nexport JAVA_HOME" %dirtmp)
		f.close()
		#call(["JAVA_HOME=/opt/java/%s/bin" %dirtmp])
		#call(["export JAVA_HOME"])



#Versão do Java
versao = java()[0]
update = java()[1]

'''
#link para baixar o JDK 8u66
http://download.oracle.com/otn-pub/java/jdk/8u66-b17/jdk-8u66-linux-i586.rpm
http://download.oracle.com/otn-pub/java/jdk/8u66-b17/jdk-8u66-linux-i586.tar.gz
http://download.oracle.com/otn-pub/java/jdk/8u66-b17/jdk-8u66-linux-x64.rpm
http://download.oracle.com/otn-pub/java/jdk/8u66-b17/jdk-8u66-linux-x64.tar.gz

'''
#=============================================================
# Método Principal
#======================================


print
print """ 
*****************************************************
              DOWNLOAD AND INSTALL JAVA FOR LINUX
                  version %s by Hinfo
*****************************************************
""" %VERSION


print "Java Versão %s update %s" % (versao, update)
print("""Escolha uma das opções:
	1 - Linux RPM (Red Hat, Fedora e derivados)
	2 - Linux i386(586/686) (Arquivo tar será instalado na pasta /opt/java)
	3 - Linux 64 RPM (Red Hat, Fedora e derivados 64 bits)
	4 - Linux 64 (Arquivo tar 64 bits| será instalado na pasta /opt/java)
	5 - Alternativa para Debian/Ubuntu
	""" )

# Espera-se que escolha uma das alternativas.
# Versão 2.0 terá validade de entrada, vá que alguém não saiba ler, :)

choice = int(raw_input('Opção: '))

#Define a versão a ser instalada
if choice == 1:
	file_java = "java-linux-i586.rpm"
	version = downloadPlataform("rpm")
	mode = 'rpm'
elif choice == 2:
	file_java = "java-linux-i586.tar.gz"
	version = downloadPlataform("tar")
	mode = 'tar'
elif choice == 3:
	file_java = "java-linux-64.rpm"
	version = downloadPlataform("x64rpm")
	mode = 'rpm'
elif choice == 4:
	file_java = "java-linux-64.tar.gz"
	version = downloadPlataform("x64")
	mode = 'tar'
elif choice == 5:
	# Para os derivados de Debian/Ubuntu
	print "Tem sim." 
	mode = 'debian'

else:
	print "Nenhuma de suas escolhas satifaz o menu!"


#Versão escolhida, hands on.
cont = 0
if choice > 0 and choice < 5:
	for line in html:
		
		if version in line:
			
			cont = cont + 1
			break
	print "Encontrado %s ocorrência(s)" %cont
	if cont > 0:
		url2 = "http://javadl.sun.com/webapps/download/AutoDL?BundleId="+version
		print "Baixando arquivo: "+file_java+" ...."
		download = urllib.urlretrieve(url2, file_java)  
		print "Download Concluído"
	else :
		print "nenhuma ocorrência encontrada!"
		exit()
		 
	
else: 
	print "Vamos instalar o java!"
	

#Chama os instaladores
if mode == 'rpm':
	install_rpm(file_java)
	
elif mode == 'tar':
	install_tar(file_java)
	
else:
	install_debian()



#mostra a versão do java
call(["java", "-version"])

print ".:.       \m/       .:."

#Finish .:.
