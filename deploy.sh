export projectVersion=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
export img=gabrielbessadev/ecommerce-service:$projectVersion
docker build --tag $img .
docker push $img
docker rmi -f $(docker images -f "dangling=true" -q)