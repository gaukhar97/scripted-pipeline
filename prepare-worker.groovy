properties([
    parameters([
        string(defaultValue: '', description: 'Input node IP', name: 'SSHNODE', trim: true)
        ])
    ])
node{
     withCredentials([sshUserPrivateKey(credentialsId: 'Jenkins', keyFileVariable: 'SSHKEY', passphraseVariable: '', usernameVariable: 'SSHUSERNAME')]) {
         stage("Initialize") {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } yum install epel-release -y "
        }
         stage("Install Java") {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } yum install java-1.8.0-openjdk-devel -y "
        }
         stage("Install Java") {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } yum install git -y "
        }
        stage("Install Ansible") {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } yum install ansible -y "
        }
        stage("Install Terraform") {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } yum install 'yum install -y wget unzip && wget https://releases.hashicorp.com/terraform/0.13.1/terraform_0.13.1_linux_amd64.zip && unzip terraform_0.13.1_linux_amd64.zip && mv terraform /usr/bin/' "
        }
    }    
} 
