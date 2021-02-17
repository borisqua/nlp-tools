@echo off
@cls
rem set utf-8 codepage
@chcp 65001
rem set windows-1251
rem @chcp 1251

gradlew.bat clean build
java -jar app/build/libs/app.jar $1
