/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.pdf.viewer;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import org.jpedal.PdfDecoder;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * PDF Viewer using jpedal
 * @author Low Heng Sin
 *
 */
public class PDFViewerBean extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -365936659584244L;
	
	private static final transient Logger logger = LogManager.getLogger(PDFViewerBean.class);
	
	private final PdfDecoder decoder = new PdfDecoder();
    private final JScrollPane center = new JScrollPane(decoder);
    private final JTextField pageField = new JTextField(2);
    private final JLabel pageCountLabel = new JLabel("00");
    private final JComboBox rotationSelect = new JComboBox(new String[] {"0", "90", "180", "270"});
    private final JComboBox zoomSelect;
    private final float[] zoomFactors = new float[] {
            0.25f, 0.33f, 0.50f, 0.75f, 1.00f, 1.50f, 2.00f, 4.00f, 8.00f};

    private final Action printAction = new AbstractAction() {
        /**
		 * 
		 */
		private static final long serialVersionUID = -4038401603459821668L;

		@Override
		public void actionPerformed(final ActionEvent e) {
            print();
        }
    };
    
    private final Action saveAction = new AbstractAction() {
        /**
		 * 
		 */
		private static final long serialVersionUID = 2556454917786013951L;

		@Override
		public void actionPerformed(final ActionEvent e) {
            save();
        }
    };
    
    private final Action goFirstAction = new AbstractAction() {
        /**
		 * 
		 */
		private static final long serialVersionUID = 3012006964079877706L;

		@Override
		public void actionPerformed(final ActionEvent e) {
            goFirst();
        }
    };
    
    private final Action goPreviousAction = new AbstractAction() {
        /**
		 * 
		 */
		private static final long serialVersionUID = 7146121058674779580L;

		@Override
		public void actionPerformed(final ActionEvent e) {
            goPrevious();
        }
    };
    
    private final Action goNextAction = new AbstractAction() {
        /**
		 * 
		 */
		private static final long serialVersionUID = -535856649012053593L;

		@Override
		public void actionPerformed(final ActionEvent e) {
            goNext();
        }
    };
    
    private final Action goLastAction = new AbstractAction() {
        /**
		 * 
		 */
		private static final long serialVersionUID = -9129162509234933692L;

		@Override
		public void actionPerformed(final ActionEvent e) {
            goLast();
        }
    };

    private final Action zoomInAction = new AbstractAction() {
        /**
		 * 
		 */
		private static final long serialVersionUID = 8712227061870874820L;

		@Override
		public void actionPerformed(final ActionEvent e) {
            zoomIn();
        }
    };

    private final Action zoomOutAction = new AbstractAction() {
        /**
		 * 
		 */
		private static final long serialVersionUID = -796771923722993041L;

		@Override
		public void actionPerformed(final ActionEvent e) {
            zoomOut();
        }
    };

    private final Action rotateCClockAction = new AbstractAction() {
        /**
		 * 
		 */
		private static final long serialVersionUID = -5323310183497748731L;

		@Override
		public void actionPerformed(final ActionEvent e) {
            rotateCClock();
        }
    };

    private final Action rotateClockAction = new AbstractAction() {
        /**
		 * 
		 */
		private static final long serialVersionUID = 7048804716310413300L;

		@Override
		public void actionPerformed(final ActionEvent e) {
            rotateClock();
        }
    };

    private String filename;
    private int currentPage = 1;
    private int scaleStep = 3;
    private int rotation = 0;

	private File tmpFile = null;

    public PDFViewerBean() {
        final String[] zoomLabels = new String[zoomFactors.length];
        for (int i = 0; i < zoomFactors.length; i++) {
            zoomLabels[i] = Integer.toString((int) (zoomFactors[i] * 100));
        }
        zoomSelect = new JComboBox(zoomLabels);
        
        zoomSelect.addActionListener(new ActionListener() {
            private boolean isAdjusting = false;
            @Override
			public void actionPerformed(final ActionEvent e) {
                if (isAdjusting) {
                    return;
                }
                isAdjusting = true;
                try {
                    setScaleStep(zoomSelect.getSelectedIndex());
                } finally {
                    isAdjusting = false;
                }
            }
        });
        
        rotationSelect.addActionListener(new ActionListener() {
            private boolean isAdjusting = false;
            @Override
			public void actionPerformed(final ActionEvent e) {
                if (isAdjusting) {
                    return;
                }
                isAdjusting = true;
                try {
                    setRotation(rotationSelect.getSelectedIndex() * 90);
                } finally {
                    isAdjusting = false;
                }
            }
        });
        
        setLayout(new BorderLayout());
        createToolBar();
        add(BorderLayout.CENTER, center);
        pageField.addFocusListener(new FocusAdapter() {        
            @Override
			public void focusGained(final FocusEvent e) {
                pageField.selectAll();
            }
        });
        pageField.setHorizontalAlignment(SwingConstants.TRAILING);
        pageField.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(final ActionEvent e) {
                setCurrentPage(Integer.parseInt(pageField.getText()));
            }
        });
        
        setPreferredSize(new Dimension(480, 0));
    }
    
    public void setRotation(final int rotation) {
        this.rotation = rotation;
        rotationSelect.setSelectedIndex(rotation / 90);
        updateZoomRotate();
    }

    public void goFirst() {
        setCurrentPage(1);
    }

    public void goPrevious() {
        setCurrentPage(currentPage - 1);
    }

    public void goNext() {
        setCurrentPage(currentPage + 1);
    }

    public void goLast() {
        setCurrentPage(decoder.getPageCount());
    }
    
    public void setCurrentPage(final int page) {
        if (page < 1 || page > decoder.getPageCount()) {
            return;
        }
        
        final Cursor oldCursor = getCursor();
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            decoder.setPageParameters(zoomFactors[scaleStep], page);
            decoder.decodePage(page);
            setRotation(decoder.getPdfPageData().getRotation(page));
            currentPage = page;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            setCursor(oldCursor);
        }
        
        goFirstAction.setEnabled(currentPage > 1);
        goPreviousAction.setEnabled(currentPage > 1);
        goNextAction.setEnabled(currentPage < decoder.getPageCount());
        goLastAction.setEnabled(currentPage < decoder.getPageCount());
        
        pageField.setText(Integer.toString(currentPage));
    }
    
    public void zoomIn() {
        setScaleStep(scaleStep + 1);
    }
    
    public void zoomOut() {
        setScaleStep(scaleStep - 1);
    }
    
    public void rotateCClock() {
        rotationSelect.setSelectedIndex(
                (rotationSelect.getSelectedIndex() + 3) % 4);
    }
    
    public void rotateClock() {
        rotationSelect.setSelectedIndex(
                (rotationSelect.getSelectedIndex() + 1) % 4);
    }
    
    public void save() {
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileFilter() {
        
            @Override
			public String getDescription() {
                return "PDF File";
            }
        
            @Override
			public boolean accept(final File f) {
                return f.isDirectory()
                    || f.getName().toLowerCase().endsWith(".pdf");
            }
        
        });
        
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        File targetFile = fc.getSelectedFile();
        if (!targetFile.getName().toLowerCase().endsWith(".pdf")) {
            targetFile =
                new File(targetFile.getParentFile(), targetFile.getName() + ".pdf");
        }
        if (targetFile.exists()) {
            if (JOptionPane.showConfirmDialog(this,
                                              "Do you want to overwrite the file?")
                != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        try {
            final InputStream is = new FileInputStream(filename);
            try {
                final OutputStream os = new FileOutputStream(targetFile);
                try {
                    final byte[] buffer = new byte[32768];
                    for (int read; (read = is.read(buffer)) != -1; ) {
                        os.write(buffer, 0, read);
                    }
                } finally {
                    os.close();
                }
            } finally {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        //decoder.enableScaledPrinting(false);
        printJob.setPageable(decoder);
        final PageFormat pf = printJob.defaultPage();
        decoder.setPageFormat(pf);
        decoder.setTextPrint(PdfDecoder.TEXTGLYPHPRINT);
        printJob.setPrintable(decoder, pf);
        if (printJob.printDialog()) {
            final Cursor oldCursor = getCursor();
            try {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                printJob.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            } finally {
                setCursor(oldCursor);
            }
        }
    }

    protected void createToolBar() {
        final JToolBar bar = new JToolBar();
        
        bar.add(createActionButton(printAction,
                                   null,
                                   "22x22/document-print.png",
                                   "Print document"));
        bar.add(createActionButton(saveAction,
                                   null,
                                   "22x22/document-save.png",
                                   "Save document"));
        
        //bar.addSeparator(new Dimension(10,0));
        
        bar.add(createActionButton(goFirstAction,
                                   null,
                                   "22x22/go-first.png",
                                   "First page"));
        bar.add(createActionButton(goPreviousAction,
                                   null,
                                   "22x22/go-previous.png",
                                   "Previos page"));
        final JPanel pagePanel = createToolbarItemPanel(pageField);
        
        bar.add(pagePanel);
        //bar.add(new JLabel("/"));
        //bar.add(pageCountLabel);
        bar.add(createActionButton(goNextAction,
                                   null,
                                   "22x22/go-next.png",
                                   "Next page"));
        bar.add(createActionButton(goLastAction,
                                   null,
                                   "22x22/go-last.png",
                                   "Last page"));
        
        //bar.addSeparator(new Dimension(10,0));
        
        bar.add(createActionButton(zoomOutAction,
                                   null,
                                   "22x22/zoom-out.png",
                                   "Next page"));
        bar.add(createToolbarItemPanel(zoomSelect));
        bar.add(createActionButton(zoomInAction,
                                   null,
                                   "22x22/zoom-in.png",
                                   "Next page"));
        
        //bar.addSeparator(new Dimension(10,0));
        
        bar.add(createActionButton(rotateCClockAction,
                                   null,
                                   "22x22/rotate-cclock.png",
                                   "Next page"));
        bar.add(createToolbarItemPanel(rotationSelect));
        bar.add(createActionButton(rotateClockAction,
                                   null,
                                   "22x22/rotate-clock.png",
                                   "Next page"));
        
        bar.setFloatable(false);
        add(BorderLayout.NORTH, bar);
    }

    protected JPanel createToolbarItemPanel(final JComponent component) {
        final JPanel pagePanel = new JPanel(new GridBagLayout());
        pagePanel.add(component);
        pagePanel.setMaximumSize(pagePanel.getPreferredSize());
        return pagePanel;
    }
    
    public boolean loadPDF(final String filename) {
        this.filename = filename;
        boolean loaded = false;
        try {
        	decoder.closePdfFile();
            decoder.openPdfFile(filename);
            pageCountLabel.setText(decoder.getPageCount() + " ");
            setCurrentPage(1);
            loaded = true;
        } catch (Exception e)
        {
        	logger.info(e.getLocalizedMessage(), e);
            loaded = false;
        }
        
        return loaded;
    }
    
    protected JButton createActionButton(final Action action,
                                         final String text,
                                         final String image,
                                         final String tooltip) {
        final ImageIcon icon =
            new ImageIcon(getClass().getResource(image));
        final double colorFactor = 0.9;
        
        final RGBImageFilter filter = new RGBImageFilter() {
            @Override
			public int filterRGB(final int x, final int y, final int rgb) {
                final int alpha = (rgb >> 24) & 0xff;
                final int red   = (rgb >> 16) & 0xff;
                final int green = (rgb >>  8) & 0xff;
                final int blue  = (rgb      ) & 0xff;
                return ((int) (alpha * colorFactor) << 24)
                     | ((int) (red   * colorFactor) << 16)
                     | ((int) (green * colorFactor) << 8)
                     | ((int) (blue  * colorFactor));
            }
        };
        
        final ImageIcon darkerIcon = new ImageIcon(
                Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(icon.getImage().getSource(),
                                                filter)));
        final JButton result = new JButton();
        result.setAction(action);
        result.setText(text);
        result.setIcon(darkerIcon);
        result.setBorderPainted(false);
        result.setHorizontalTextPosition(SwingConstants.CENTER);
        result.setVerticalTextPosition(SwingConstants.BOTTOM);
        result.setMnemonic(0);
        result.setToolTipText(tooltip);

        final Dimension dim = result.getPreferredSize();
        result.setMaximumSize(new Dimension(32, dim.height));

        result.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseEntered(final MouseEvent me) {
                result.setBorderPainted(true);
                result.setIcon(icon);
            }
            @Override
			public void mouseExited(final MouseEvent me) {
                result.setBorderPainted(false);
                result.setIcon(darkerIcon);
            }
        });
        
        result.setBorderPainted(false);
        result.setFocusPainted(false);

        return result;
    }

    public int getCurrentPage() {
        return currentPage;
    }
    
    public void clearDocument() {
    	decoder.closePdfFile();
    	if (tmpFile != null) {
    		tmpFile.delete();
    		tmpFile = null;
    	}
    }
    
    public void setScaleStep(final int scaleStep) {
        if (scaleStep < 0 || zoomFactors.length <= scaleStep) {
            return;
        }
        
        this.scaleStep = scaleStep;
        zoomSelect.setSelectedIndex(scaleStep);
        updateZoomRotate();
    }

    protected void updateZoomRotate() {
        final Cursor oldCursor = getCursor();
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            decoder.setPageParameters(zoomFactors[scaleStep],
                                      currentPage,
                                      rotation);
            decoder.invalidate();
            decoder.repaint();
            zoomInAction.setEnabled(scaleStep < zoomFactors.length - 1);
            zoomOutAction.setEnabled(scaleStep > 0);
        } finally {
            setCursor(oldCursor);
        }
    }

    public void setScale(final int percent) {
        int step;
        for (step = 0; step < zoomFactors.length - 1; step++) {
            if (zoomFactors[step] * 100 >= percent) {
                break;
            }
        }
    	setScaleStep(step);
    }

    public boolean loadPDF(final byte[] data)
    {
    	return loadPDF(new ByteArrayInputStream(data));
    }

    public boolean loadPDF(final InputStream is)
    {
    	if (tmpFile != null) {
    		tmpFile.delete();
    	}
    	
        try {
            tmpFile = File.createTempFile("adempiere", ".pdf");
            tmpFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        try {
			final OutputStream os = new FileOutputStream(tmpFile);
            try {
                final byte[] buffer = new byte[32768];
                for (int read; (read = is.read(buffer)) != -1; ) {
                    os.write(buffer, 0, read);
                }
            } catch (IOException e) {
				e.printStackTrace();
			} finally {
                os.close();
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return loadPDF(tmpFile.getAbsolutePath());
    }

	@Override
	protected void finalize() throws Throwable {
    	if (tmpFile != null) {
    		tmpFile.delete();
    	}
    	decoder.closePdfFile();
	}
}
