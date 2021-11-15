import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MyClientTop extends JFrame implements MouseListener {
	private Container c;
	private ImageIcon top, start;
	private ImageIcon icon1, icon2;
	static int iconnum = 1;
	static String playerMyName;//���[�U�̖��O
	static String playerIpAdress;
	private JButton startbutton;//�X�^�[�g�{�^��
	private JButton icon01, icon02;//player


	public MyClientTop() {
		//���O�̓��̓_�C�A���O���J��
		playerMyName = JOptionPane.showInputDialog(null,"���O����͂��Ă�������","���O�̓���",JOptionPane.QUESTION_MESSAGE);
		if(playerMyName.equals("")){
			playerMyName = "No name";//���O���Ȃ��Ƃ��́C"No name"�Ƃ���
		}
		//IP�A�h���X�̓���
		playerIpAdress = JOptionPane.showInputDialog(null,"IP�A�h���X����͂��Ă�������","IP�A�h���X�̓���",JOptionPane.QUESTION_MESSAGE);
		if(playerIpAdress.equals("")){
		playerIpAdress = "localhost";
		}



		//�E�B���h�E���쐬����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�E�B���h�E�����Ƃ��ɁC����������悤�ɐݒ肷��
		setTitle("MyClientTop");//�E�B���h�E�̃^�C�g����ݒ肷��
		setSize(880,600);//�E�B���h�E�̃T�C�Y��ݒ肷��
		c = getContentPane();//�t���[���̃y�C�����擾����
		c.setBackground(new Color(135, 206, 250));

		//�A�C�R���̐ݒ�
		top = new ImageIcon("title.png");
		start = new ImageIcon("start.png");
		icon1 = new ImageIcon("fishicon1.png");
		icon2 = new ImageIcon("fishicon2.png");

		c.setLayout(null);//�������C�A�E�g�̐ݒ���s��Ȃ�


		//�g�b�v�摜
        JLabel theLabel = new JLabel(top);
        c.add(theLabel);
        theLabel.setBounds(220,30,440,200);

		//���O
		JLabel nameLabel = new JLabel(playerMyName);//���O�p���x��
		c.add(nameLabel);
		nameLabel.setBounds(330,230,200,50);
		nameLabel.setFont(new Font( "���C���I",Font.BOLD,30));
		nameLabel.setForeground(Color.BLACK);
		nameLabel.setBackground(new Color(135, 206, 250));
		nameLabel.setOpaque(true);

		//�v���C���[�I��
		icon01 = new JButton(icon1);
		c.add(icon01);
		icon01.setBounds(330,290,100,100);
		icon01.addMouseListener(this);

		icon02 = new JButton(icon2);
		c.add(icon02);
		icon02.setBounds(450,290,100,100);
		icon02.addMouseListener(this);

		//�X�^�[�g�{�^��
		startbutton = new JButton(start);
		c.add(startbutton);
		startbutton.setBounds(250,400,400,100);
		startbutton.addMouseListener(this);

	}

	public static void main(String[] args) {
		MyClientTop net = new MyClientTop();
		net.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {//�{�^�����N���b�N�����Ƃ��̏���

	System.out.println("�N���b�N");
	JButton theButton = (JButton)e.getComponent();//�N���b�N�����I�u�W�F�N�g�𓾂�D�^���Ⴄ�̂ŃL���X�g����

	Icon theIcon = theButton.getIcon();//theIcon�ɂ́C���݂̃{�^���ɐݒ肳�ꂽ�A�C�R��������
	System.out.println(theIcon);//�f�o�b�O�i�m�F�p�j�ɁC�N���b�N�����A�C�R���̖��O���o�͂���

	if(theIcon == start){
		MyClient mc = new MyClient();//MyClient���N��
		System.out.println("�͂��߂܂�");
		mc.setVisible(true);//MyClient��������
		c.setVisible(false); //�E�B���h�E����
		dispose();
	}else if(theIcon == icon1){//�v���C���[�A�C�R���̐ݒ�
		iconnum = 1;
		System.out.println(iconnum);

	}else if(theIcon == icon2){//�v���C���[�A�C�R���̐ݒ�
		iconnum = 2;
		System.out.println(iconnum);

	}

	}
	public void mouseEntered(MouseEvent e) {//�}�E�X���I�u�W�F�N�g�ɓ������Ƃ��̏���
	}

	public void mouseExited(MouseEvent e) {//�}�E�X���I�u�W�F�N�g����o���Ƃ��̏���
	}

	public void mousePressed(MouseEvent e) {//�}�E�X�ŃI�u�W�F�N�g���������Ƃ��̏����i�N���b�N�Ƃ̈Ⴂ�ɒ��Ӂj
	}

	public void mouseReleased(MouseEvent e) {//�}�E�X�ŉ����Ă����I�u�W�F�N�g�𗣂����Ƃ��̏���
	}



}


