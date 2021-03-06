properties([
    parameters([
        string(defaultValue: '', description: 'Provide node IP', name: 'node', trim: true)
        ])
    ])

node {
    stage("Pull Repo"){
        sh "rm -rf ansible-melodi && git clone https://github.com/gaukhar97/ansible-melodi.git"
    }   

    stage("Install Melodi"){
        ansiblePlaybook become: true, colorized: true, credentialsId: 'Jenkins', disableHostKeyChecking: true, inventory: "${params.node}," , playbook: 'ansible-melodi/main.yml'
    }
}
