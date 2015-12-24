安卓App热补丁动态修复技术

思想来源：QQ空间终端开发团队
http://zhuanlan.zhihu.com/magilu/20308548

一个ClassLoader可以包含多个dex文件，每个dex文件是一个Element，多个dex文件排列成一个有序的数组dexElements，
当找类的时候，会按顺序遍历dex文件，然后从当前遍历的dex文件中找类，如果找类则返回，如果找不到从下一个dex文件继续查找。

在此基础上构想了热补丁的方案，把有问题的类打包到一个dex（patch.dex）中去，然后把这个dex插入到Elements的最前面。
