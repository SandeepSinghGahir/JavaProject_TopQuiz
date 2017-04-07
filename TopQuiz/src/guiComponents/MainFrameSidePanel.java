package guiComponents;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import central.QuizController;

public class MainFrameSidePanel extends JPanel implements ActionListener {

	private CentralQuizCardPanel parentPanel;

	public MainFrameSidePanel() {

		this.setBorder(new EmptyBorder(20, 10, 20, 0));

		// MainFrameSidePanel.setLayout(new GridBagLayout());
		// MainFrameSidePanel.setBorder(new EmptyBorder((int)
		// (MainFrameSidePanelHeight * 0.2), 0, 0, 0));
		// c.insets = new Insets((int)(MainFrameSidePanelHeight * 0.1), (int)
		// (MainFrameSidePanelWidth * 0.3),
		// (int) (MainFrameSidePanelHeight * 0.1),(int) (MainFrameSidePanelWidth
		// * 0.3));
		// c.gridwidth = 1;
		// c.gridheight = 1;
		Dimension df = this.getSize();
		
		GridBagConstraints c = new GridBagConstraints();
		JButton cButton = new JButton("C            ");
		cButton.addActionListener(this);
		cButton.setActionCommand(QuizController.C);

		JButton cPlusButton = new JButton("C++        ");
		cPlusButton.addActionListener(this);
		cPlusButton.setActionCommand(QuizController.C_PLUS);

		JButton javaButton = new JButton("Java         ");
		javaButton.addActionListener(this);
		javaButton.setActionCommand(QuizController.JAVA);

		/*
		 * JButton mixedBagButton = new JButton("Mixed Bag");
		 * mixedBagButton.addActionListener(this);
		 * mixedBagButton.setActionCommand(QuizController.MIXED_BAG);
		 */

		GroupLayout gl_MainFrameSidePanel = new GroupLayout(this);
		gl_MainFrameSidePanel.setHorizontalGroup(gl_MainFrameSidePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_MainFrameSidePanel.createSequentialGroup().addGroup(gl_MainFrameSidePanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_MainFrameSidePanel.createSequentialGroup().addContainerGap().addComponent(cButton))
						/*
						 * .addGroup(gl_MainFrameSidePanel.createSequentialGroup
						 * () .addContainerGap() .addComponent(mixedBagButton))
						 */
						.addGroup(gl_MainFrameSidePanel.createSequentialGroup().addContainerGap()
								.addComponent(cPlusButton))
						.addGroup(gl_MainFrameSidePanel.createSequentialGroup().addContainerGap()
								.addComponent(javaButton)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		gl_MainFrameSidePanel.setVerticalGroup(gl_MainFrameSidePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_MainFrameSidePanel.createSequentialGroup().addContainerGap().addComponent(cButton)
						.addGap(27).addComponent(cPlusButton).addGap(26).addComponent(javaButton).addGap(31)
		/*
		 * .addComponent(mixedBagButton) .addContainerGap()
		 */));

		this.setLayout(gl_MainFrameSidePanel);
	}

	public void setParentPanel(CentralQuizCardPanel centralQuizCardPanel) {
		this.parentPanel = centralQuizCardPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println(e.getActionCommand() + " has been selected");
		String subject = e.getActionCommand();
		parentPanel.checkAnswer();
		parentPanel.getQuestion(subject);

	}
}
