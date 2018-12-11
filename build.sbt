import com.typesafe.sbt.SbtNativePackager.autoImport.NativePackagerHelper._
import com.typesafe.sbt.packager.docker.Cmd

organization := "com.idorsia.research.arcite"

name := "arcite-???"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.8"

crossScalaVersions := Seq(scalaVersion.value, "2.12.1")

resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.file("ivy local", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns),
  Resolver.bintrayRepo("typesafe", "maven-releases"),
  Resolver.jcenterRepo,
  Resolver.sonatypeRepo("public"),
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
  MavenRepository("mvn-repository", "https://mvnrepository.com/artifact/"),
  MavenRepository("Artima Maven Repository", "http://repo.artima.com/releases/"))

// These options will be used for *all* versions.
scalacOptions ++= Seq(
  "-deprecation"
  , "-unchecked"
  , "-encoding", "UTF-8"
  , "-Xlint"
  //  , "-Yclosure-elim"
  //  , "-Yinline"
  , "-Xverify"
  , "-feature"
  , "-language:postfixOps"
)

libraryDependencies ++= {
  val akkaVersion = "2.5.3"
  val akkaHttpVersion = "10.0.9"

  Seq(
    "com.idorsia.research.arcite" %% "arcite-core" % "1.91.6",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-agent" % akkaVersion,
    "com.typesafe.akka" %% "akka-camel" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
    "com.typesafe.akka" %% "akka-contrib" % akkaVersion,
    "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion,
    "com.typesafe.akka" %% "akka-osgi" % akkaVersion,
    "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
    "com.typesafe.akka" %% "akka-persistence-tck" % akkaVersion,
    "com.typesafe.akka" %% "akka-remote" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-jackson" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-xml" % akkaHttpVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "org.slf4j" % "slf4j-api" % "1.7.1",
    "org.slf4j" % "log4j-over-slf4j" % "1.7.1",  // for any java classes looking for this
    "org.iq80.leveldb" % "leveldb" % "0.9",
    "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
    "commons-io" % "commons-io" % "2.5",
    "org.scalacheck" %% "scalacheck" % "1.13.5" % "test",
    "org.specs2" %% "specs2-core" % "3.8.9" % "test",
    "org.scalactic" %% "scalactic" % "3.0.1",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "com.github.agourlay" %% "cornichon" % "0.11.2" % "test",
    "org.json4s" %% "json4s-jackson" % "3.5.1")
}

enablePlugins(JavaServerAppPackaging)
//enablePlugins(JavaAppPackaging)
//enablePlugins(DockerPlugin)
//enablePlugins(DockerSpotifyClientPlugin)

mainClass in Compile := Some("com.idorsia.research.arcite.???.Main")

//we wipe out all previous settings of docker commands
dockerCommands := Seq(
  Cmd("FROM", "centos:7"),
  Cmd("MAINTAINER", "Bernard Deffarges bernard.deffarges@idorsia.com"),
  Cmd("RUN", "rm -f /etc/localtime && ln -s /usr/share/zoneinfo/Europe/Zurich /etc/localtime"),
  Cmd("RUN", "yum update -y && yum install -y unzip && yum install -y wget && yum clean all"),
  Cmd("RUN",
    """wget --no-cookies --no-check-certificate \
      |--header "Cookie: oraclelicense=accept-securebackup-cookie" \
      |"http://download.oracle.com/otn-pub/java/jdk/8u141-b15/336fa29ff2bb4ef291e347e091f7f4a7/jdk-8u141-linux-x64.rpm" \
      |-O /tmp/jdk-8-linux-x64.rpm""".stripMargin),
  Cmd("RUN", "yum -y install /tmp/jdk-8-linux-x64.rpm"),
  Cmd("RUN", "rm -rf /tmp/*"),
  Cmd("ENV", "JAVA_HOME /usr/java/latest"),
// TODO put your own stuff here if needed
  Cmd("ENV", "LC_ALL en_US.UTF-8"),
  Cmd("ENV", "LANG en_US.UTF-8"),
  Cmd("RUN", "groupadd arcite -g 987654"),
  Cmd("RUN", "useradd --uid 987654 --gid 987654 arcite"),
  Cmd("WORKDIR", "/opt/docker"),
  Cmd("COPY", "opt /opt"),
  Cmd("RUN", "chown -R arcite:arcite ."),
  Cmd("USER", "arcite"),
  Cmd("ENTRYPOINT", "bin/arcite-???"))

// or based on debian / openjdk with R code:
//dockerCommands := Seq(
//  Cmd("FROM", "openjdk:8-jdk"),
//  Cmd("MAINTAINER", "Bernard Deffarges bernard.deffarges@idorsia.com"),
//  Cmd("ENV", "LC_ALL en_US.UTF-8"),
//  Cmd("ENV", "LANG en_US.UTF-8"),
//  Cmd("ENV", "R_BASE_VERSION 3.4.1"),
//  Cmd("RUN", "echo Europe/Berlin > /etc/timezone && dpkg-reconfigure --frontend noninteractive tzdata"),
//  Cmd("RUN", "addgroup -gid 987654 arcite"),
//  Cmd("RUN", "useradd --home-dir /home/arcite --uid 987654 --gid 987654 arcite"),
//  Cmd("RUN", "mkdir /home/arcite && chown arcite:arcite /home/arcite"),
//  Cmd("RUN",
//    """apt-get update && apt-get install -y --no-install-recommends \
//      |ed less locales vim-tiny wget ca-certificates fonts-texgyre  \
//      |&& rm -rf /var/lib/apt/lists/*""".stripMargin),
//  Cmd("RUN",
//    """echo "en_US.UTF-8 UTF-8" >> \
//      |/etc/locale.gen && locale-gen en_US.utf8 \
//      |&& /usr/sbin/update-locale LANG=en_US.UTF-8""".stripMargin),
//  Cmd("RUN",
//    """echo deb http://http.debian.net/debian sid main > /etc/apt/sources.list.d/debian-unstable.list \
//      |&& echo 'APT::Default-Release testing;' > /etc/apt/apt.conf.d/default""".stripMargin),
//  Cmd("RUN", """apt-get update && apt-get install -t unstable -y libudunits2-dev"""),
//  Cmd("RUN",
//    """apt-get update && apt-get install -t unstable -y \
//      |--no-install-recommends --fix-missing libcurl4-openssl-dev \
//      |libssl-dev haskell-platform texlive pandoc littler libxml2 libxml2-dev libcairo2-dev \
//      |r-cran-littler r-base=${R_BASE_VERSION}* \
//      |r-base-dev=${R_BASE_VERSION}* r-recommended=${R_BASE_VERSION}* \
//      |&& echo 'options(repos = c(CRAN = "https://cran.rstudio.com/"), \
//      |download.file.method = "libcurl")' >> /etc/R/Rprofile.site \
//      |&& echo 'source("/etc/R/Rprofile.site")' >> /etc/littler.r \
//      |&& ln -s /usr/share/doc/littler/examples/install.r /usr/local/bin/install.r \
//      |&& ln -s /usr/share/doc/littler/examples/install2.r /usr/local/bin/install2.r \
//      |&& ln -s /usr/share/doc/littler/examples/installGithub.r /usr/local/bin/installGithub.r \
//      |&& ln -s /usr/share/doc/littler/examples/testInstalled.r /usr/local/bin/testInstalled.r \
//      |&& install.r docopt && rm -rf /tmp/downloaded_packages/ /tmp/*.rds \
//      |&& rm -rf /var/lib/apt/lists/*""".stripMargin),
//  Cmd("RUN",
//    """R -e "install.packages(c('gplots', 'rmarkdown', 'DT', 'lattice',\
//      |'chron', 'grid', 'data.table', 'ggplot2', 'corrplot', 'stringr', 'devtools',\
//      |'plotly','heatmaply', 'prettydoc', 'Rmisc', 'reshape2', 'gtools'),\
//      |repos='https://cran.rstudio.com/', dependencies=TRUE)" """.stripMargin),
//  Cmd("RUN",
//    """R -e "source('https://bioconductor.org/biocLite.R'); \
//      |biocLite(c('impute'))" """.stripMargin),
//  Cmd("WORKDIR", "/opt/docker"),
//  Cmd("COPY", "opt /opt"),
//  Cmd("RUN", "chown -R arcite:arcite ."),
//  Cmd("USER", "arcite"),
//  Cmd("ENTRYPOINT", "bin/arcite-???"))

javaOptions in Universal ++= Seq(
  // -J params will be added as jvm parameters
  //  "-J-Xmx64m",
  //  "-J-Xms64m",

  //   others will be added as app parameters
  //  "-Dconfig.resource=docker_test.conf"

  // you can access any build setting/task here
  //  s"-version=${version.value}"
)

mappings in Universal ++= {
  // optional example illustrating how to copy additional directory
  directory("scripts") ++
    // copy configuration files to config directory
    contentOf("src/main/resources").toMap.mapValues("config/" + _) ++
    contentOf("src/main/R").toMap.mapValues("R/" + _)
}

dockerRepository := Some("nexus-docker.idorsia.com")

dockerAlias := DockerAlias(dockerRepository.value, Some("arcite"), packageName.value, Some(version.value))

dockerExposedPorts := Seq(2600)

licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0")))

bashScriptExtraDefines += """addJava "-Dconfig.resource=$ARCITE_???_CONF""""



