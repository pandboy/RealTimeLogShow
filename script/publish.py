#!/usr/bin/python3
import paramiko
import _thread
import time
import os
import sys

ip = "10.45.0.1"
user = "root"
password = "123456"
home = '/Data/software/tomcat'
current = home + "/webapp"
release  = home + "/bak"
port = 22
local_dir='/Data/deploy/ROOT.war'
remote_dir='/Data/deploy/ROOT.war'
def execute_cmds(ip, user, password, cmd):
	try:
		ssh = paramiko.SSHClient()
		ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
		ssh.connect(ip, port, user, password, timeout = 8)
		sftp = paramiko.SFTPClient.from_transport(ssh.get_transport())
		sftp = ssh.open_sftp()
		sftp.put(local_dir, remote_dir)
		for m in cmd:
			print(m)
			stdin, stdout, stderr = ssh.exec_command(m)
			out = stdout.readlines()
			for o in out:
				print(o)
				print('%s\tOK\n'%(ip))
		ssh.close()
	except Exception as e:
		raise	

def transfer_file(ip, user, password):
	try:
		ssh=paramiko.SSHClient()
		ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
		ssh.connect(ip, port, user, password, timeout = 9)
		sftp = paramiko.SFTPClient.from_transport(ssh.get_transport())
		sftp = ssh.open_sftp()
		sftp.put(local_dir, remote_dir)
		ssh.close()
	except Exception as e:
		raise e
if __name__=='__main__':
	print('Start deploying to server %s'%(ip))
	now = time.strftime("%Y%m%d%H%M%S")
	publish_cmd = [
                'sh /home/pandboy/publish.sh'
	]
	rollback_cmd = [
                'sh /home/pandboy/rollback.sh',
                ]
	params =  sys.argv
	print(params)
	cmd = publish_cmd
	if len(params) >= 3:
		ip = params[1]
		password = params[2]
		cmdtype = params[3]
		#1表示生产发布0,其他表示回滚
		if (cmdtype == '1'):
			cmd = publish_cmd
		else:
			cmd = rollback_cmd
		
	else:
		print("param is valid")
		sys.exit(0)
	print(cmd)
	execute_cmds(ip, user, password, cmd)