package net.sf.memoranda.ui.htmleditor;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class SrcDialog extends JDialog {
	JPanel panel1 = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	JScrollPane jScrollPane1 = new JScrollPane();
	JTextArea jTextArea1 = new JTextArea();

	public SrcDialog(Frame frame, String text) {
		super(frame, "Source text", true);
		try {
			setText(text);
			jbInit();
			pack();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public SrcDialog() {
		this(null, "");
	}
	
	void jbInit() throws Exception {
		panel1.setLayout(borderLayout1);
		jTextArea1.setEditable(false);
		getContentPane().add(panel1);
		panel1.add(jScrollPane1, BorderLayout.CENTER);
		jScrollPane1.getViewport().add(jTextArea1, null);
	}

	public void setText(String txt) {
		jTextArea1.setText(txt);
	}
}