#!/bin/bash

cmd_path=$(cd "$(dirname "$0")"; pwd)
dst_file=$cmd_path/build/classes.aar
libs_path=$cmd_path/libs
tmp_path=$cmd_path/build/classes

## 创建新的tmp目录
rm -f $dst_file
rm -rf $tmp_path
mkdir -p $tmp_path

## 进入tmp目录
pushd $tmp_path > /dev/null

## 解压libs
for f in $libs_path/*.aar; do
    jar xvf $f > /dev/null
done

## 退出tmp目录
popd > /dev/null

## 合并jar完成，开始合并aar

dst_file=$cmd_path/build/classes.aar
libs_path=$cmd_path/build/classes
tmp_path=$cmd_path/build/aar

## 创建新的tmp目录
rm -f $dst_file
rm -rf $tmp_path
mkdir -p $tmp_path
mkdir -p $tmp_path/jni/armeabi-v7a

cp -r $libs_path/jni/armeabi-v7a $tmp_path/jni/
cp $libs_path/classes.jar $tmp_path/
cp $libs_path/AndroidManifest.xml $tmp_path/

## 进入tmp目录
pushd $tmp_path > /dev/null

## 压缩tmp目录里所有文件
jar cvf $dst_file * > /dev/null

if [ -f $dst_file ]
then
  echo SUCCESS: $dst_file
else
  echo FAILURE: $dst_file is not exists
fi

## 退出tmp目录
popd > /dev/null