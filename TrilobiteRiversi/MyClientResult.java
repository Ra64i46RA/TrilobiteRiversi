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

public class MyClientResult extends JFrame implements MouseListener {
	private Container c;
	private ImageIcon result, end;
	int iconnum = 1;
	String playerMyName;//���[�U�̖��O
	String playerIpAdress;//IP�A�h���X
	private JButton startbutton;//�X�^�[�g�{�^��
	int iconNum, resultNum;//���ʗp


	public MyClientResult() {

		//�g�b�v�y�[�W�����p��
		String myName = MyClientTop.playerMyName;
		iconNum = MyClientTop.iconnum;

		//�Q�[�����̈��p��
		int resultNum = MyClient.resultNum;
		int mynum = MyClient.mynum;
		int enenum = MyClient.enemynum;

		//�E�B���h�E���쐬����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�E�B���h�E�����Ƃ��ɁC����������悤�ɐݒ肷��
		setTitle("MyClientResult");//�E�B���h�E�̃^�C�g����ݒ肷��
		setSize(880,600);//�E�B���h�E�̃T�C�Y��ݒ肷��
		c = getContentPane();//�t���[���̃y�C�����擾����
		c.setBackground(new Color(135, 206, 250));

		//�A�C�R���̐ݒ�
		if(resultNum == 1){
			result = new ImageIcon("win.png");
		}else if(resultNum == 2){
			result = new ImageIcon("lose.png");
		}else{
			result = new ImageIcon("draw.png");
		}
		end = new ImageIcon("end.png");

		c.setLayout(null);//�������C�A�E�g�̐ݒ���s��Ȃ�

		//���ʉ摜
	    JLabel theLabel = new JLabel(result);
	    c.add(theLabel);
	    theLabel.setBounds(220,30,440,200);

		//�X�R�A�\��
		String myscore = String.valueOf(mynum);
		JLabel myScore = new JLabel(myscore);
		c.add(myScore);
		myScore.setBounds(220,710,200,50);
		myScore.setFont(new Font( "���C���I",Font.BOLD,30));
		myScore.setOpaque(true);

		String enemyscore = String.valueOf(enenum);
		JLabel enemyScore = new JLabel(enemyscore);
		c.add(enemyScore);
		enemyScore.setBounds(440,710,200,50);
		enemyScore.setFont(new Font( "���C���I",Font.BOLD,30));
		enemyScore.setOpaque(true);


		//�I���{�^��
		startbutton = new JButton(end);
		c.add(startbutton);
		startbutton.setBounds(250,400,400,100);
		startbutton.addMouseListener(this);

	}

	public static void main(String[] args) {
		MyClientResult net = new MyClientResult();
		net.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {//�{�^�����N���b�N�����Ƃ��̏���

		System.out.println("�N���b�N");
		JButton theButton = (JButton)e.getComponent();//�N���b�N�����I�u�W�F�N�g�𓾂�D�^���Ⴄ�̂ŃL���X�g����

		Icon theIcon = theButton.getIcon();//theIcon�ɂ́C���݂̃{�^���ɐݒ肳�ꂽ�A�C�R��������
		System.out.println(theIcon);//�f�o�b�O�i�m�F�p�j�ɁC�N���b�N�����A�C�R���̖��O���o�͂���

		if(theIcon == end){//�I��
			System.out.println("�I���܂�");
			c.setVisible(false); //�E�B���h�E����
			dispose();
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



