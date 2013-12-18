This is a demo for testing audio effect: BassBoost, Virtualizer and left and right tuning in headset.

低音增强：BassBoost
立体声：Virtualizer

关于声音调节：
AudioManager调节绝对音量：

audioManager .setStreamVolume(AudioManager.STREAM_MUSIC, volumeValue, 0);
音量类型是AudioManager.STREAM_MUSIC
该类型表示是对多媒体音乐播放音量的整体控制。

其音量数值可以通过对应的getStreamVolume方法获得。
可以通过audioManager .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
 
获取最大数值，如果把SeekBar的最大数值设为改值，则可方便地用SeekBar进行音量调节。

MediaPlayer调节相对音量：
MediaPlayer中有如下方法声明：
     public native void setVolume( float leftVolume, float rightVolume);
经网上很多的资料说明，该方法的参数范围是0.0到1.0.

但是mediaPlayer并没有提供相应的get方法，所以获取不到实际的值。

经过实际的程序实践，发现调节左右声道的音量值并不会引起上面AudioManager得到的全局音量。
但是全局音量的改变会影响左右声道值的大小。
并且如果不使用MediaPlayer设置左右声道的音量值，只用AudioManager调节，从耳机中听到的效果是左右声道都设为最大值的效果。
即左右声道默认值都是1.0.

所以可以理解如下：
MediaPlayer调节的左右声道音量值只是在全局音量值的基础上进行一个比例缩放。
比如全局音量值为globalVolume,
那么，默认情况下：
leftVolumeValue = globalVolume * 1.0f;
rightVolumeValue = globalVolume * 1.0f;
用mediaPlayer.setVolume(leftVolume,rightVolume);调节之后：
leftVolumeValue = globalVolume * leftVolume;
rightVolumeValue = globalVolume * rightVolume;
其中leftVolume和rightVolume为方法传进来的浮点型比率值。

