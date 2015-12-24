安卓App热补丁动态修复技术

思想来源：QQ空间终端开发团队
http://zhuanlan.zhihu.com/magilu/20308548

著作权归作者所有。
商业转载请联系作者获得授权，非商业转载请注明出处。
作者：M.A.G.I
链接：http://zhuanlan.zhihu.com/magilu/20308548
来源：知乎

一个ClassLoader可以包含多个dex文件，每个dex文件是一个Element，多个dex文件排列成一个有序的数组dexElements，
当找类的时候，会按顺序遍历dex文件，然后从当前遍历的dex文件中找类，如果找类则返回，如果找不到从下一个dex文件继续查找。
