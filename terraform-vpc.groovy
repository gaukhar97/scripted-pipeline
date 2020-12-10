properties([
    parameters([
        booleanParam(defaultValue: true, description: 'are you sure you wanna run this command?', name: 'terraform_apply'), 
        booleanParam(defaultValue: false, description: 'are you sure you wanna run this command?', name: 'terraform_destroy'),
        choice(choices: ['dev', 'qa', 'prod'], description: 'Choose environment', name: 'environment')
    ])
])

node{
    stage("Pull Repo"){
        cleanWs()
        git branch: 'master', url: 'https://github.com/gaukhar97/terraform-vpc.git'
    }
    
    withEnv(['AWS_REGION=us-east-1']) {
        withCredentials([usernamePassword(credentialsId: 'aws_jenkins_key', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
            stage("Terraform Init"){
                sh """
                    terraform init
                """
            }

            if(params.terraform_apply){
                stage("Terraform Apply"){
                    sh """
                        terraform apply -var-file ${param.environment}.tfvars -auto-approve
                    """
                } 
            }
            else if(params.terraform_destroy){
                stage("Terraform Destroy"){
                    sh """
                        terraform destroy -var-file ${param.environment}.tfvars -auto-approve
                    """
                }
            }
            else {
                stage("Terraform Plan"){
                    sh """
                        terraform plan -var-file ${param.environment}.tfvars
                    """
                }   
            }     
        }
    }      
} 