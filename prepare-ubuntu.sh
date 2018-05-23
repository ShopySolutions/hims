
sudo apt-get update
sudo apt-get install build-essential
sudo apt-get install libpng-dev libjpeg-dev
ssh-keygen -t rsa
sudo apt-get install git
curl -sL https://deb.nodesource.com/setup_0.12 | sudo bash -
sudo apt-get install nodejs
sudo apt install nodejs-legacy
sudo npm install -g --upgrade npm
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
sudo apt-get install oracle-java8-set-default
sudo apt-get install maven

sudo npm install -g grunt
sudo npm install -g grunt-cli
sudo npm install -g karma
sudo npm install -g karma-cli
sudo npm install --save-dev gulp
sudo npm install --global gulp
sudo npm install -g bower
sudo npm install -g node-sass
sudo npm install -g gulp-sass
sudo npm install --save-dev gulp-bower
sudo npm install -g yo
sudo npm install -g generator-jhipster


For Gulp Issue :
npm install --save-dev gulp-sass@latest
Delete your node_modules folder
Remove gulp-sass from your package.json file
Remove node-sass from your package.json file (if you have it in there)
Run npm install gulp-sass --save-dev



