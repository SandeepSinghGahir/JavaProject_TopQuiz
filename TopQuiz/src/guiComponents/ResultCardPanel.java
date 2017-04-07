package guiComponents;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import central.QuizController.Topic;
import model.QuizSession;

/**
 * 
 * ResultCardPanel.java
 * 
 * @author swabhat This class draws a graphical representation of the points
 *         scored in each topic.
 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
 */
public class ResultCardPanel extends JPanel {

	private JPanel parentPanel;
	private HashMap<Topic, QuizSession> quizResultMap;
	private Map<Color, Integer> bars;
	private int panelHeight, topPanelHeight, legendPanelWidth, panelWidth, barPanelHeight, barPanelWidth, barWidth,
			unitHeight;
	private static final int BORDER_GAP = 40;
	private Graphics2D g2d;
	private static final int PER_TOPIC_QUESTIONS = 10; // total number of
														// questions in each
														// topic in the question
														// bank
	private static final Color WRONG_COLOR = Color.RED;
	private static final Color RIGHT_COLOR = Color.GREEN;

	public ResultCardPanel(HashMap<Topic, QuizSession> quizResultMap) {
		this.quizResultMap = quizResultMap;
		this.setBorder(new EmptyBorder(30, 30, 30, 30));

	}

	public void setParentPanel(JPanel mainFrameCentralPanel) {
		this.parentPanel = mainFrameCentralPanel;
	}

	public JPanel getParentPanel() {
		return parentPanel;
	}

	/**
	 * This method divides the canvas into 3 scetions
	 * <ul>
	 * <li>The top section displays the result
	 * <li>One side section displays the graph for each topic
	 * <li>The other side section displays the legend for the graph
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g2d = (Graphics2D) g;

		Dimension df = this.getSize();
		panelHeight = (int) df.getHeight();
		panelWidth = (int) df.getWidth();
		topPanelHeight = panelHeight / 4;
		barPanelWidth = panelWidth / 4 * 3;
		legendPanelWidth = panelWidth / 4;
		barPanelHeight = panelHeight / 4 * 3;
		// barPanelStartHeight = barPanelHeight + topPanelHeight;
		// barPanelEndHeight = barPanelStartHeight + barPanelHeight

		// Write the basic quiz result info
		drawTextualScoreDisplay();

		// createBarGraphPanel
		createBarGraph();

		// create legend
		createLegend();

	}

	/*
	 * Write the basic quiz result info on the Result Panel
	 */
	private void drawTextualScoreDisplay() {

		int totalNumOfQuestions = 0;
		int totalNumRightAnswer = 0;
		int totalScore = 0;
		int totalNumWrongAnswer = 0;
		for (Entry<Topic, QuizSession> quizResultMap : quizResultMap.entrySet()) {
			QuizSession quizSession = quizResultMap.getValue();
			totalNumOfQuestions += quizSession.getNumTotalQuestions();
			totalNumRightAnswer += quizSession.getNumRightAnswers();
			totalNumWrongAnswer += quizSession.getNumWrongAnswers();
			totalScore += quizSession.getTopicScore();
		}
		int numAttempted = totalNumWrongAnswer + totalNumRightAnswer;
		String resultText1 = "Score = " + totalScore;
		String resultText3 = "You attemped " + numAttempted + " questions, out of total number of "+ totalNumOfQuestions + " questions";
		String resultText2 = "You correctly answered " + totalNumRightAnswer + " questions";

		int startX = 50;
		Font font = new Font("SansSerif", Font.BOLD, 12);
		g2d.setFont(font);
		int fontAscent = g2d.getFontMetrics().getMaxAscent();
		int rowHeight = topPanelHeight / 5;
		int startY = rowHeight * 2 - fontAscent; // start from 2nd row;
		g2d.drawString(resultText1, (int) startX, (int) startY);
		startY += rowHeight;
		g2d.drawString(resultText2, (int) startX, (int) startY);
		startY += rowHeight;
		g2d.drawString(resultText3, (int) startX, (int) startY);

	}

	/*
	 * Calculates the points for the bar and draws the graph
	 */
	private void createBarGraph() {

		int axisStartPointX = BORDER_GAP;
		int axisStartPointY = topPanelHeight + barPanelHeight - BORDER_GAP;
		int xAxisEndPointX = barPanelWidth - BORDER_GAP;
		int xAxisEndPointY = topPanelHeight + barPanelHeight - BORDER_GAP;
		int yAxisEndPointX = BORDER_GAP;
		int yAxisEndPointY = topPanelHeight + BORDER_GAP;
		g2d.setStroke(new BasicStroke(3.0f));
		Line2D lineXAxis = new Line2D.Double(axisStartPointX, axisStartPointY, xAxisEndPointX, xAxisEndPointY);
		g2d.draw(lineXAxis);
		Line2D lineYAxis = new Line2D.Double(axisStartPointX, axisStartPointY, yAxisEndPointX, yAxisEndPointY);
		g2d.draw(lineYAxis);

		barWidth = (barPanelWidth - BORDER_GAP * 2) / (quizResultMap.size() * 2);
		unitHeight = (barPanelHeight - BORDER_GAP * 2) / PER_TOPIC_QUESTIONS;
		int startX = barWidth + axisStartPointX;

		// calculate height of each bar and draw it
		for (Entry<Topic, QuizSession> quizResultMap : quizResultMap.entrySet()) {
			QuizSession quizSession = quizResultMap.getValue();

			int numRightAns = quizSession.getNumRightAnswers();
			int numWrongAns = quizSession.getNumWrongAnswers();
			int rightAnsHeight = numRightAns * unitHeight;
			int wrongAnsHeight = numWrongAns * unitHeight;

			int rightStartY = axisStartPointY - rightAnsHeight - 1; // 3 is
																	// stroke
																	// width
			int wrongStartY = rightStartY - wrongAnsHeight;

			drawBar(quizSession.getTopicName(), numRightAns, numWrongAns, startX, rightStartY, wrongStartY,
					rightAnsHeight, wrongAnsHeight);
			System.out.println("Topic = " + quizSession.getTopicName().toString() + "\t Num Right Answers = "
					+ quizSession.getNumRightAnswers() + "\t Num  Wrong Answers = " + quizSession.getNumWrongAnswers());
			startX += 2 * barWidth;

		}

	}

	/*
	 * draw Bar for the given topic
	 */

	private void drawBar(Topic topic, int numRightAns, int numWrongAns, int startX, int rightStartY, int wrongStartY,
			int rightAnsHeight, int wrongAnsHeight) {

		// Right Answer

		// System.out.println(unitHeight);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(startX, rightStartY, barWidth, rightAnsHeight);
		g2d.setColor(RIGHT_COLOR);
		g2d.fillRect(startX, rightStartY, barWidth, rightAnsHeight);
		g2d.setPaint(Color.BLACK);
		g2d.drawString(Integer.toString(numRightAns), startX + barWidth / 2, rightStartY + rightAnsHeight / 3);

		// Wrong Answer
		if (wrongAnsHeight > unitHeight) {
			g2d.setColor(Color.BLACK);
			g2d.drawRect(startX, wrongStartY, barWidth, wrongAnsHeight);
			g2d.setColor(WRONG_COLOR);
			g2d.fillRect(startX, wrongStartY, barWidth, wrongAnsHeight);
			g2d.setPaint(Color.BLACK);
			g2d.drawString(Integer.toString(numWrongAns), startX + barWidth / 2, wrongStartY + wrongAnsHeight / 3);
		}

		// Write Topic Name
		Font font = new Font("Arial", Font.BOLD, 12);
		g2d.setFont(font);
		g2d.setPaint(Color.BLACK);
		FontMetrics metrics = g2d.getFontMetrics(font);
		String topicName = topic.toString();
		int textWidthHf = metrics.stringWidth(topicName) / 2;
		int textAscent = metrics.getMaxAscent();
		int startPointX = startX + (barWidth / 2 - textWidthHf);
		g2d.drawString(topic.toString(), startPointX, topPanelHeight + barPanelHeight - textAscent);
	}

	/*
	 * draws the legend for the bar graph
	 */
	private void createLegend() {

		// create Font for Account name
		Font font = new Font("SansSerif", Font.BOLD, 12);
		g2d.setFont(font);

		// get FontMetrics for calculating offsets and
		// positioning descriptions
		FontMetrics metrics = getFontMetrics(font);
		int ascent = metrics.getMaxAscent();
		int startX = barPanelWidth;
		int startY = topPanelHeight;
		int spaceBtwnLegends = 30;

		// draw description for each data point

		// Right Answer :
		g2d.setColor(RIGHT_COLOR);
		g2d.fillRect(startX, startY, ascent, ascent);

		// Write Right Answer Description
		int startX2 = startX + ascent * 2;
		g2d.setColor(Color.black);
		g2d.drawString("Number of ", startX2, startY);
		startY += ascent + 5;
		g2d.drawString("Right answers ", startX2, startY);

		// Wrong Answer :
		int startY2 = startY + spaceBtwnLegends;
		g2d.setColor(WRONG_COLOR);
		g2d.fillRect(startX, startY2, ascent, ascent);

		// Write Wrong Answer Description
		g2d.setColor(Color.black);
		g2d.drawString("Number of ", startX2, startY2);
		startY2 += ascent + 5;
		g2d.drawString("Wrong answers ", startX2, startY2);

	}

}
