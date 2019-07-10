appName = "test-service"
appDescription = "test"
appPort = 18100
dubboPort = 30882
QaDeployHostIp = "localhost"
ProdDeployHostIp = ["192.168.95.24", "192.168.95.25"]
def jenkins

node {
  stage 'Build'
  checkout scm
  jenkins = load 'jenkins.groovy'
  jenkins.buildDockerImage()
}

timeout(time: 1, unit: 'DAYS') {
  stage 'Promotion'
  input '你确定要部署到qa环境吗?'
}

stage name: 'Deploy to QA', concurrency: 1
node {
  jenkins.deploy('qa', QaDeployHostIp)
}

timeout(time: 10, unit: 'SECONDS') {
  stage 'Promotion'
  input '你确定要部署到prod环境吗?'
}

stage name: 'Deploy to prod', concurrency: 1
node {
  for (int i = 0; i < ProdDeployHostIp.size(); i++) {
    stage name: 'Deploy to host:' + ProdDeployHostIp[i]
    jenkins.deploy('prod', ProdDeployHostIp[i])
  }
}
