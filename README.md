# READEME

想要在studio中直接调试java程序，需要修改.idea/gradle.xml
添加<option name="delegatedBuild" value="false" />

<GradleProjectSettings>
        <option name="delegatedBuild" value="false" />
        <option name="testRunner" value="PLATFORM" />
        ...
      </GradleProjectSettings>

查看任务栈命令：
adb shell dumpsys activity activities
