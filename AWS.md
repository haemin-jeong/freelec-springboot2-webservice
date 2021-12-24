# AWS 클라우드 서비스
클라우드 서비스는 인터넷(클라우드)을 통해 서버, 스토리지, 데이터베이스, 네트워크, 소프트웨어, 모니터링 등의 컴퓨팅 서비스를 제공하는 것이다.

클라우드 형태  
1. Infrastructure as a Service(IaaS, 아이아스, 이에스)
    - 물리 장비를 미들웨어와 함께 묶어둔 추상화 서비스
    - 가상 머신, 스토리지, 네트워크, 운영체제 등의 IT 인프라를 대여해주는 서비스
    - AWS의 EC2, S3 등
2. Platform as a Service(PaaS, 파스)
    - IaaS에서 한번 더 추상화한 서비스 -> 많은 기능이 자동화
    - AWS의 Beanstalk, Heroku
3. Software as a Service(SaaS, 사스)
    - 구글 드라이브, 드롭박스, 와탭 등과 같은 소프트웨어 서비스

## EC2 인스턴스 생성하기
인스턴스란 EC2 서비스에 생성된 가상머신을 말한다.

### 리전 설정
- 리전이란 AWS의 서비스가 구동될 지역. 해당 지역의 데이터 센터에 구추고딘 가성머신들을 사용한다.
- 서울 리전이 있기 떄문에, 국내에서 서비스 한다면 서울을 선택하자.

### Name 태그 추가
- Name 태그를 추가하여 웹 콘솔에서 인스턴스를 구분하는 이름을 붙혀줄 수 있다.
- 내 서비스의 인스턴스를 나타낼 수 있는 값으로 등록

### 보안 그룹 추가
- 보안 그룹은 '서버로 80 포트 외에는 허용하지 않는다'라는 방화벽을 이야기한다.
- 보안 그룹 이름은 유의미한 이름으로 변경(예: freelec-springboot2-webservice-ec2)
- SSH 포트번호가 22인 경우는 AWS EC2에 터미널로 접속할 때를 말하는데, 전체 오픈(0.0.0.0/0, ::/0) 하지말고 내 IP에서만 접속할 수 있도록 선택하고 집 외에 다른 장소에서 접속할 때는 그때 해당 장소의 IP를 SSH 규칙에 추가하여 사용하는 것이 안전하다.
    - pem 키가 없으면 접속할 수 없기 때문에 전체 오픈으로 설정하는 경우 혹시나 실수로 pem키가 노출되는 순간 문제가 된다. 

### pem 키
- 인스턴스에 접근하려면 pem키(비밀키)가 필요하기 때문에 잃어 버리면 안된다.
- 인스턴스는 pem키(비밀키)와 일치하는 공개키를 가지고 있다.

### 고정 IP 사용하기 - EIP
인스턴스도 하나의 서버이기 떄문에 인스턴스 생성 시에 IP가 할당된다. 그런데 인스턴스를 중지하고 다시 시작할때도 새로운 IP가 다시 할당된다.

AWS에서 고정 IP를 Elastic IP(EIP, 탄력적 IP)한다.

탄력적 IP 할당하기
1. 인스턴스 웹페이지 좌측 카테코리에서 탄력적 IP 선택
2. 새 주소 할당
3. 작업 -> 주소 연결

**주의 사항: 탄력적 IP는 생성하고 EC2 서버에 연결하지 않으면 비용이 발생한다. 그렇기 때문에 탄력적 IP는 무조건 EC2에 바로 연결하고, 사용하지 않을때 바로 삭제해야한다.**

## EC2 서버 접속하기
AWS와 같은 외부 서버로 SSH 접속을 하기 위한 기본적인 명령어는 다음과 같다.
```
ssh -i pem 키 위치 EC2의 탄력적 IP 주소
```
그런데 매번 이렇게 긴 명령어를 타이핑하는 것은 귀찮은 일이다.

ssh에 간편하게 접속할 수 있도록 설정할 수 있다.
1. pem 키 파일을 `~/.ssh/` 경로로 복사한다. 
    - `cp 키 위치 ~/.ssh/`
    - 해당 디렉토리로 pem 키 파일을 옮겨 놓으면 ssh 실행시 pem 키 파일을 자동으로 읽어 접속하기 때문에 별도로 키 위치를 적어주지 않아도 된다.
2. pem 키의 권한 변경
    - `chdmoe 600 ~/.ssh/pem키 파일명
3. pem 키가 있는 `~/ssh` 디렉토리에 config 파일 생성
```
# config

Host 본인이 원하는 서비스명(앞으로 접속에 사용할 값)
    HostName ec2의 탄력적 IP 주소
    User ec2-user
    IdentityFile ~/.ssh/pem키 이름
```
4. config 파일 실행 권한 설정
```
chmod 700 ~/.ssh/config
```
위와 같은 설정 과정을 거치면 `ssh config에 등록한 서비스명`으로 EC2에 접속할 수 있게 된다.  
예를 들어 앞서 config 파일에 Host를 hello로 등록했다면 `ssh hello` 로 접속할 수 있다.

## 아마존 리눅스 서버 생성시 꼭 해야하는 설정

### 자바8 설치
Java 8 설치
```
sudo yum install -y java-1.8.0-openjdk-devel.x86_64
```
Java 버전 8로 변경
```
sudo /usr/sbin/alternatives --config java
```

### 타임존 변경
EC2 서버의 기본 시간대는 UTC이기 떄문에 한국과 9시간의 차이가 난다. 그렇기 떄문에 서버의 타임존을 KST(한국 시간)으로 변경해줘야 한다.
```
타임존 설정
sudo rm /etc/localtime
sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime

타임존 확인
date
```

### Hostname 변경
여러 서버를 관리 중일 경우 터미널에서 IP만으로는 어떤 서비스의 서버인지 구분하기 어렵다. Hostname을 변경하여 IP가 아닌 서비스 명으로 표시되도록 변경할 수 있다.

참고: 책의 내용은 Amazon Linux AMI 기준으로 나와있는데 현재 Amazon Linux2로만 생성이 가능하다. 그런데 Amazon Linux2 호스트명 변경 방법이 책에 나외있는 방법과 다르다. hostnamectl 명령어를 이용해서 변경해야 한다.

변경 방법 : https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/set-hostname.html

Hostname을 변경 후에 호스트 주소를 찾을 때 가장 먼저 검색 해보는 /etc/hosts에도 변경한 hostname을 등록해줘야 한다.  

/etc/hosts 파일을 열어 `127.0.0.1 등록한 HOSTNAME`을 추가해주자.

`curl 등록한 HOSTNAME`명령어를 사용하여 정상적으로 등록되었는지 확인하자.
