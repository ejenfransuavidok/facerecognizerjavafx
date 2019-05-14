# facerecognizerjavafx
recognize faces with javafx and neural web
Так как присутствует нативная библиотека, возможно (не уверен) потребуютя некоторые небольшие корректировки под конкретную 
платформу. Я тестировал на ubuntu 14.0.

Свидетельство успешности сборки проекта:

vidok@vidok-Inspiron-5558o:~/javaprojects/mts$ mvn clean install
[INFO] Scanning for projects...
[WARNING] 
[WARNING] Some problems were encountered while building the effective model for groupId:mts:jar:1.0-SNAPSHOT
[WARNING] 'build.plugins.plugin.version' for org.apache.maven.plugins:maven-compiler-plugin is missing. @ line 100, column 21
[WARNING] 
[WARNING] It is highly recommended to fix these problems because they threaten the stability of your build.
[WARNING] 
[WARNING] For this reason, future Maven versions might no longer support building such malformed projects.
[WARNING] 
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building mts 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ mts ---
[INFO] Deleting /home/vidok/javaprojects/mts/target
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ mts ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 267 resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ mts ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
[INFO] Compiling 11 source files to /home/vidok/javaprojects/mts/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ mts ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /home/vidok/javaprojects/mts/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ mts ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ mts ---
[INFO] No tests to run.
[INFO] 
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ mts ---
[INFO] Building jar: /home/vidok/javaprojects/mts/target/mts-1.0-SNAPSHOT.jar
[INFO] 
[INFO] --- maven-install-plugin:2.4:install (default-install) @ mts ---
[INFO] Installing /home/vidok/javaprojects/mts/target/mts-1.0-SNAPSHOT.jar to /home/vidok/.m2/repository/groupId/mts/1.0-SNAPSHOT/mts-1.0-SNAPSHOT.jar
[INFO] Installing /home/vidok/javaprojects/mts/pom.xml to /home/vidok/.m2/repository/groupId/mts/1.0-SNAPSHOT/mts-1.0-SNAPSHOT.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 22.093 s
[INFO] Finished at: 2019-05-14T22:49:54+03:00
[INFO] Final Memory: 30M/329M
[INFO] ------------------------------------------------------------------------

Свидетельство успешности запуска проекта

vidok@vidok-Inspiron-5558o:~/javaprojects/mts$ mvn exec:java
[INFO] Scanning for projects...
[WARNING] 
[WARNING] Some problems were encountered while building the effective model for groupId:mts:jar:1.0-SNAPSHOT
[WARNING] 'build.plugins.plugin.version' for org.apache.maven.plugins:maven-compiler-plugin is missing. @ line 100, column 21
[WARNING] 
[WARNING] It is highly recommended to fix these problems because they threaten the stability of your build.
[WARNING] 
[WARNING] For this reason, future Maven versions might no longer support building such malformed projects.
[WARNING] 
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building mts 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] >>> exec-maven-plugin:1.2.1:java (default-cli) > validate @ mts >>>
[INFO] 
[INFO] <<< exec-maven-plugin:1.2.1:java (default-cli) < validate @ mts <<<
[INFO] 
[INFO] --- exec-maven-plugin:1.2.1:java (default-cli) @ mts ---
sample.Main.main() INFO  factory.Nd4jBackend - Loaded [CpuBackend] backend
 sample.Main.main() INFO  nativeblas.NativeOpsHolder - Number of threads used for NativeOps: 2
 sample.Main.main() INFO  nativeblas.Nd4jBlas - Number of threads used for BLAS: 2
 sample.Main.main() INFO  executioner.DefaultOpExecutioner - Backend used: [CPU]; OS: [Linux]
 sample.Main.main() INFO  executioner.DefaultOpExecutioner - Cores: [4]; Memory: [1.7GB];
 sample.Main.main() INFO  executioner.DefaultOpExecutioner - Blas vendor: [MKL]
 sample.Main.main() INFO  graph.ComputationGraph - Starting ComputationGraph with WorkspaceModes set to [training: ENABLED; inference: ENABLED], cacheMode set to [NONE]
 sample.Main.main() INFO  face.FaceRecognition - 
 
 https://yadi.sk/i/GgVq2FumGj4MvQ
