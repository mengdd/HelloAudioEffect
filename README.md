This is a demo for testing audio effect: BassBoost, Virtualizer and left and right tuning in headset.

������ǿ��BassBoost
��������Virtualizer

�����������ڣ�
AudioManager���ھ���������

audioManager .setStreamVolume(AudioManager.STREAM_MUSIC, volumeValue, 0);
����������AudioManager.STREAM_MUSIC
�����ͱ�ʾ�ǶԶ�ý�����ֲ���������������ơ�

��������ֵ����ͨ����Ӧ��getStreamVolume������á�
����ͨ��audioManager .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
 
��ȡ�����ֵ�������SeekBar�������ֵ��Ϊ��ֵ����ɷ������SeekBar�����������ڡ�

MediaPlayer�������������
MediaPlayer�������·���������
     public native void setVolume( float leftVolume, float rightVolume);
�����Ϻܶ������˵�����÷����Ĳ�����Χ��0.0��1.0.

����mediaPlayer��û���ṩ��Ӧ��get���������Ի�ȡ����ʵ�ʵ�ֵ��

����ʵ�ʵĳ���ʵ�������ֵ�����������������ֵ��������������AudioManager�õ���ȫ��������
����ȫ�������ĸı��Ӱ����������ֵ�Ĵ�С��
���������ʹ��MediaPlayer������������������ֵ��ֻ��AudioManager���ڣ��Ӷ�����������Ч����������������Ϊ���ֵ��Ч����
����������Ĭ��ֵ����1.0.

���Կ���������£�
MediaPlayer���ڵ�������������ֵֻ����ȫ������ֵ�Ļ����Ͻ���һ���������š�
����ȫ������ֵΪglobalVolume,
��ô��Ĭ������£�
leftVolumeValue = globalVolume * 1.0f;
rightVolumeValue = globalVolume * 1.0f;
��mediaPlayer.setVolume(leftVolume,rightVolume);����֮��
leftVolumeValue = globalVolume * leftVolume;
rightVolumeValue = globalVolume * rightVolume;
����leftVolume��rightVolumeΪ�����������ĸ����ͱ���ֵ��

