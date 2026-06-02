# 1. 用 JDK 编译这个 Java 文件到当前临时目录 (注意路径与你的项目对齐)
javac -encoding UTF-8 sdpsdk/src/main/java/com/cbj/sdpsdk/DimenTool2.java

# 2. 直接用 java 虚拟机运行它 (Cp 指向源码根目录)
java -cp sdpsdk/src/main/java com.cbj.sdpsdk.DimenTool2