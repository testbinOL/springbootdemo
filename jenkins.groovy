//--------------------------公共函数----------------------------------------


def buildDockerImage() {
    stage name: 'Build Docker Image', concurrency: 1
    echo '开始构建Docker镜像'
    echo "tag: ${env.BUILD_TAG}"

    stage name: 'Push Docker Image', concurrency: 1
    echo '开始推送Docker镜像'
    sh "docker ps | grep springboodemo"
    echo '推送结束'
}

def deploy(environment, hostIp) {
    echo "开始部署${environment}环境，部署地址${hostIp}"
    echo "${environment}部署完成"
}

this
