properties([
    parameters([
        booleanParam(defaultValue: true, description: 'are you sure you wanna run this command?', name: 'terraform_apply'), 
        booleanParam(defaultValue: false, description: 'are you sure you wanna run this command?', name: 'terraform_destroy')
    ])
])

node{
    stage("Pull Repo"){
        git branch: 'solution', url: 'https://github.com/ikambarov/terraform-task.git'
    }
    
    dir('sandbox/') {
        withCredentials([usernamePassword(credentialsId: 'aws_jenkins_key', passwordVariable: 'AWS_ACCESS_SECRET_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
            stage("Terraform Init"){
                sh """
                    terraform init
                """
            }

        if(params.terraform_apply){
            stage("Terraform Apply"){
                sh """
                    terraform apply  -auto-approve
                """
            }
        }
        else if(params.terraform_destroy){
            stage("Terraform Destroy"){
                sh """
                    terraform destroy -auto-approve
                """
            }
        }
        else {
            stage("Terraform Plan"){
                sh """
                    terraform plan
                """
                }
            }   
        }     
    }
} 