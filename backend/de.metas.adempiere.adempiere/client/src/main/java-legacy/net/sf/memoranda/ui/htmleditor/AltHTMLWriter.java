/**
 * AltHTMLWriter.java
 * Hacked version of javax.swing.text.html.HTMLWriter
 * Created on 18.02.2003, 16:27:05 Alex 
 * Package: javax.swing.text.html
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 OpenMechanics.org
 */
package net.sf.memoranda.ui.htmleditor;

import java.awt.Polygon;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.BitSet;
import java.util.Enumeration;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractWriter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Segment;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.html.CSS;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.Option;
import javax.swing.text.html.StyleSheet;

/**
 * This is an alternate writer for HTMLDocuments.
 * 
 * Based on original javax.swing.text.html.HTMLWriter: 
 * 
 * Sun Java(TM) 2 SDK, Standard Edition Version 1.4.1
 * HTMLWriter.java 1.30 01/12/03
 * author  Sunita Mani 
 * version 1.26, 02/02/00
 * Copyright 2002 Sun Microsystems, Inc.
 */

public class AltHTMLWriter extends AbstractWriter {
    /*
     * Stores all elements for which end tags have to
     * be emitted.
     */
    private Stack blockElementStack = new Stack();
    private boolean inContent = false;
    private boolean inPre = false;
    /** When inPre is true, this will indicate the end offset of the pre
     * element. */
    private int preEndOffset;
    private boolean inTextArea = false;
    private boolean newlineOutputed = false;
    private boolean completeDoc;

    /*
     * Stores all embedded tags. Embedded tags are tags that are
     * stored as attributes in other tags. Generally they're
     * character level attributes.  Examples include
     * &lt;b&gt;, &lt;i&gt;, &lt;font&gt;, and &lt;a&gt;.
     */
    private Vector tags = new Vector(10);

    /**
     * Values for the tags.
     */
    private Vector tagValues = new Vector(10);

    /**
     * Used when writing out content.
     */
    private Segment segment;

    /*
     * This is used in closeOutUnwantedEmbeddedTags.
     */
    private Vector tagsToRemove = new Vector(10);

    /**
     * Set to true after the head has been output.
     */
    private boolean wroteHead;

    /**
     * Set to true when entities (such as &lt;) should be replaced.
     */
    private boolean replaceEntities;

    /**
     * Temporary buffer.
     */
    private char[] tempChars;

    private String encoding = null;
    /**
     * Creates a new HTMLWriter.
     *
     * @param w   a Writer
     * @param doc  an HTMLDocument
     *
     */

    public AltHTMLWriter(Writer w, HTMLDocument doc) {
        this(w, doc, 0, doc.getLength(), null, false);
    }

    public AltHTMLWriter(Writer w, HTMLDocument doc, String enc) {
        this(w, doc, 0, doc.getLength(), enc, false);
    }

    public AltHTMLWriter(Writer w, HTMLDocument doc, String enc, boolean nument) {
        this(w, doc, 0, doc.getLength(), enc, nument);
    }

    boolean _nument = false;

    /**
     * Creates a new HTMLWriter.
     *
     * @param w  a Writer
     * @param doc an HTMLDocument
     * @param pos the document location from which to fetch the content
     * @param len the amount to write out
     */
    public AltHTMLWriter(Writer w, HTMLDocument doc, int pos, int len, String enc, boolean nument) {
        super(w, doc, pos, len);
        completeDoc = (pos == 0 && len == doc.getLength());
        setLineLength(80);
        this.encoding = enc;
        this._nument = nument;
    }

    /**
     * Iterates over the
     * Element tree and controls the writing out of
     * all the tags and its attributes.
     *
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     *
     */
    public void write() throws IOException, BadLocationException {
        ElementIterator it = getElementIterator();
        Element current = null;
        Element next = null;

        wroteHead = false;
        setCurrentLineLength(0);
        replaceEntities = false;
        setCanWrapLines(false);
        if (segment == null) {
            segment = new Segment();
        }
        inPre = false;
        boolean forcedBody = false;
        while ((next = it.next()) != null) {
            if (!inRange(next)) {
                if (completeDoc && next.getAttributes().getAttribute(StyleConstants.NameAttribute) == HTML.Tag.BODY) {
                    forcedBody = true;
                }
                else {
                    continue;
                }
            }
            if (current != null) {

                /*
                  if next is child of current increment indent
                */

                if (indentNeedsIncrementing(current, next)) {
                    incrIndent();
                }
                else if (current.getParentElement() != next.getParentElement()) {
                    /*
                       next and current are not siblings
                       so emit end tags for items on the stack until the
                       item on top of the stack, is the parent of the
                       next.
                    */
                    Element top = (Element) blockElementStack.peek();
                    while (top != next.getParentElement()) {
                        /*
                           pop() will return top.
                        */
                        blockElementStack.pop();
                        if (!synthesizedElement(top)) {
                            AttributeSet attrs = top.getAttributes();
                            if (!matchNameAttribute(attrs, HTML.Tag.PRE) && !isFormElementWithContent(attrs)) {
                                decrIndent();
                            }
                            endTag(top);
                        }
                        top = (Element) blockElementStack.peek();
                    }
                }
                else if (current.getParentElement() == next.getParentElement()) {
                    /*
                       if next and current are siblings the indent level
                       is correct.  But, we need to make sure that if current is
                       on the stack, we pop it off, and put out its end tag.
                    */
                    Element top = (Element) blockElementStack.peek();
                    if (top == current) {
                        blockElementStack.pop();
                        endTag(top);
                    }
                }
            }
            if (!next.isLeaf() || isFormElementWithContent(next.getAttributes())) {
                blockElementStack.push(next);
                startTag(next);
            }
            else {
                emptyTag(next);
            }
            current = next;
        }
        /* Emit all remaining end tags */

        /* A null parameter ensures that all embedded tags
           currently in the tags vector have their
           corresponding end tags written out.
        */
        closeOutUnwantedEmbeddedTags(null);

        if (forcedBody) {
            blockElementStack.pop();
            endTag(current);
        }
        while (!blockElementStack.empty()) {
            current = (Element) blockElementStack.pop();
            if (!synthesizedElement(current)) {
                AttributeSet attrs = current.getAttributes();
                if (!matchNameAttribute(attrs, HTML.Tag.PRE) && !isFormElementWithContent(attrs)) {
                    decrIndent();
                }
                endTag(current);
            }
        }

        /*if (completeDoc) {
            writeAdditionalComments();
        }*/

        segment.array = null;
    }

    /**
     * Writes out the attribute set.  Ignores all
     * attributes with a key of type HTML.Tag,
     * attributes with a key of type StyleConstants,
     * and attributes with a key of type
     * HTML.Attribute.ENDTAG.
     *
     * @param attr   an AttributeSet
     * @exception IOException on any I/O error
     *
     */
    protected void writeAttributes(AttributeSet attr) throws IOException {
        // translate css attributes to html
        convAttr.removeAttributes(convAttr);
        convertToHTML32(attr, convAttr);

        Enumeration names = convAttr.getAttributeNames();
        while (names.hasMoreElements()) {
            Object name = names.nextElement();
            if (name instanceof HTML.Tag || name instanceof StyleConstants || name == HTML.Attribute.ENDTAG) {
                continue;
            }
            write(" " + name + "=\"" + convAttr.getAttribute(name) + "\"");
        }
    }

    /**
     * Writes out all empty elements (all tags that have no
     * corresponding end tag).
     *
     * @param elem   an Element
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     */
    protected void emptyTag(Element elem) throws BadLocationException, IOException {

        if (!inContent && !inPre) {
            indent();
        }

        AttributeSet attr = elem.getAttributes();
        closeOutUnwantedEmbeddedTags(attr);
        writeEmbeddedTags(attr);

        if (matchNameAttribute(attr, HTML.Tag.CONTENT)) {
            inContent = true;
            text(elem);
        }
        else if (matchNameAttribute(attr, HTML.Tag.COMMENT)) {
            comment(elem);
        }
        else {
            boolean isBlock = isBlockTag(elem.getAttributes());
            if (inContent && isBlock) {
                writeLineSeparator();
                indent();
            }

            Object nameTag = (attr != null) ? attr.getAttribute(StyleConstants.NameAttribute) : null;
            Object endTag = (attr != null) ? attr.getAttribute(HTML.Attribute.ENDTAG) : null;

            boolean outputEndTag = false;
            // If an instance of an UNKNOWN Tag, or an instance of a
            // tag that is only visible during editing
            //
            if (nameTag != null && endTag != null && (endTag instanceof String) && ((String) endTag).equals("true")) {
                outputEndTag = true;
            }

            if (completeDoc && matchNameAttribute(attr, HTML.Tag.HEAD)) {

                if (outputEndTag) {
                    // Write out any styles.
                    writeStyles(((HTMLDocument) getDocument()).getStyleSheet());
                }
                wroteHead = true;
            }

            write('<');
            if (outputEndTag) {
                write('/');
            }
            write(elem.getName());
            writeAttributes(attr);
            write('>');

            if (matchNameAttribute(attr, HTML.Tag.TITLE) && !outputEndTag) {
                Document doc = elem.getDocument();
                String title = (String) doc.getProperty(Document.TitleProperty);
                write(title);
            }

            else if (!inContent || isBlock) {
                writeLineSeparator();
                if (isBlock && inContent) {
                    indent();
                }
            }
        }
    }

    /**
     * Determines if the HTML.Tag associated with the
     * element is a block tag.
     *
     * @param attr  an AttributeSet
     * @return  true if tag is block tag, false otherwise.
     */
    protected boolean isBlockTag(AttributeSet attr) {
        Object o = attr.getAttribute(StyleConstants.NameAttribute);
        if (o instanceof HTML.Tag) {
            HTML.Tag name = (HTML.Tag) o;
            return name.isBlock();
        }
        return false;
    }

    /**
     * Writes out a start tag for the element.
     * Ignores all synthesized elements.
     *
     * @param elem   an Element
     * @exception IOException on any I/O error
     */
    protected void startTag(Element elem) throws IOException, BadLocationException {

        if (synthesizedElement(elem)) {
            return;
        }

        // Determine the name, as an HTML.Tag.
        AttributeSet attr = elem.getAttributes();
        Object nameAttribute = attr.getAttribute(StyleConstants.NameAttribute);
        HTML.Tag name;
        if (nameAttribute instanceof HTML.Tag) {
            name = (HTML.Tag) nameAttribute;
        }
        else {
            name = null;
        }

        if (name == HTML.Tag.PRE) {
            inPre = true;
            preEndOffset = elem.getEndOffset();
        }

        // write out end tags for item on stack
        closeOutUnwantedEmbeddedTags(attr);

        if (inContent) {
            writeLineSeparator();
            inContent = false;
            newlineOutputed = false;
        }

        if (completeDoc && name == HTML.Tag.BODY && !wroteHead) {
            // If the head has not been output, output it and the styles.
            wroteHead = true;
            indent();
            write("<head>");
            writeLineSeparator();
            incrIndent();
            writeAdditionalComments();
            writeLineSeparator();
            if (encoding != null) {
                writeLineSeparator();
                write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + encoding + "\">");
            }
            writeStyles(((HTMLDocument) getDocument()).getStyleSheet());
            writeLineSeparator();
            //System.out.println(getDocument().getProperty(Document.TitleProperty));
            write("<title>" + getDocument().getProperty(Document.TitleProperty) + "</title>");

            decrIndent();
            writeLineSeparator();
            indent();
            write("</head>");
            writeLineSeparator();
        }

        indent();
        write('<');
        write(elem.getName());
        writeAttributes(attr);
        write('>');
        if (name != HTML.Tag.PRE) {
            writeLineSeparator();
        }

        if (name == HTML.Tag.TEXTAREA) {
            textAreaContent(elem.getAttributes());
        }
        else if (name == HTML.Tag.SELECT) {
            selectContent(elem.getAttributes());
        }
      //  else if (completeDoc && name == HTML.Tag.BODY) {
            // Write out the maps, which is not stored as Elements in
            // the Document.
            //writeMaps(((HTMLDocument)getDocument()).getMaps());
      //  }
        else if (name == HTML.Tag.HEAD) {
            indent();
            writeAdditionalComments();
            if (encoding != null) {
                indent();
                write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + encoding + "\">");
            }
            writeLineSeparator();
            wroteHead = true;
        }
    }

    /**
     * Writes out text that is contained in a TEXTAREA form
     * element.
     *
     * @param attr  an AttributeSet
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     */
    protected void textAreaContent(AttributeSet attr) throws BadLocationException, IOException {
        Document doc = (Document) attr.getAttribute(StyleConstants.ModelAttribute);
        if (doc != null && doc.getLength() > 0) {
            if (segment == null) {
                segment = new Segment();
            }
            doc.getText(0, doc.getLength(), segment);
            if (segment.count > 0) {
                inTextArea = true;
                incrIndent();
                indent();
                setCanWrapLines(true);
                replaceEntities = true;
                write(segment.array, segment.offset, segment.count);
                replaceEntities = false;
                setCanWrapLines(false);
                writeLineSeparator();
                inTextArea = false;
                decrIndent();
            }
        }
    }

    /**
     * Writes out text.  If a range is specified when the constructor
     * is invoked, then only the appropriate range of text is written
     * out.
     *
     * @param elem   an Element
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     */
    protected void text(Element elem) throws BadLocationException, IOException {
        int start = Math.max(getStartOffset(), elem.getStartOffset());
        int end = Math.min(getEndOffset(), elem.getEndOffset());
        if (start < end) {
            if (segment == null) {
                segment = new Segment();
            }
            getDocument().getText(start, end - start, segment);
            newlineOutputed = false;
            if (segment.count > 0) {
                if (segment.array[segment.offset + segment.count - 1] == '\n') {
                    newlineOutputed = true;
                }
                if (inPre && end == preEndOffset) {
                    if (segment.count > 1) {
                        segment.count--;
                    }
                    else {
                        return;
                    }
                }
                replaceEntities = true;
                setCanWrapLines(!inPre);
                write(segment.array, segment.offset, segment.count);
                setCanWrapLines(false);
                replaceEntities = false;
            }
        }
    }

    /**
     * Writes out the content of the SELECT form element.
     *
     * @param attr the AttributeSet associated with the form element
     * @exception IOException on any I/O error
     */
    protected void selectContent(AttributeSet attr) throws IOException {
        Object model = attr.getAttribute(StyleConstants.ModelAttribute);
        incrIndent();
        if (model instanceof OptionListModel) {
            OptionListModel listModel = (OptionListModel) model;
            int size = listModel.getSize();
            for (int i = 0; i < size; i++) {
                Option option = (Option) listModel.getElementAt(i);
                writeOption(option);
            }
        }
        else if (model instanceof OptionComboBoxModel) {
            OptionComboBoxModel comboBoxModel = (OptionComboBoxModel) model;
            int size = comboBoxModel.getSize();
            for (int i = 0; i < size; i++) {
                Option option = (Option) comboBoxModel.getElementAt(i);
                writeOption(option);
            }
        }
        decrIndent();
    }

    /**
     * Writes out the content of the Option form element.
     * @param option  an Option
     * @exception IOException on any I/O error
     *
     */
    protected void writeOption(Option option) throws IOException {

        indent();
        write('<');
        write("option");
        // PENDING: should this be changed to check for null first?
        Object value = option.getAttributes().getAttribute(HTML.Attribute.VALUE);
        if (value != null) {
            write(" value=" + value);
        }
        if (option.isSelected()) {
            write(" selected");
        }
        write('>');
        if (option.getLabel() != null) {
            write(option.getLabel());
        }
        writeLineSeparator();
    }

    /**
     * Writes out an end tag for the element.
     *
     * @param elem    an Element
     * @exception IOException on any I/O error
     */
    protected void endTag(Element elem) throws IOException {
        if (synthesizedElement(elem)) {
            return;
        }
        if (matchNameAttribute(elem.getAttributes(), HTML.Tag.PRE)) {
            inPre = false;
        }

        // write out end tags for item on stack
        closeOutUnwantedEmbeddedTags(elem.getAttributes());
        if (inContent) {
            if (!newlineOutputed) {
                writeLineSeparator();
            }
            newlineOutputed = false;
            inContent = false;
        }
        indent();
        write('<');
        write('/');
        write(elem.getName());
        write('>');
        writeLineSeparator();
    }

    /**
     * Writes out comments.
     *
     * @param elem    an Element
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     */
    protected void comment(Element elem) throws BadLocationException, IOException {
        AttributeSet as = elem.getAttributes();
        if (matchNameAttribute(as, HTML.Tag.COMMENT)) {
            Object comment = as.getAttribute(HTML.Attribute.COMMENT);
            if (comment instanceof String) {
                writeComment((String) comment);
            }
            else {
                writeComment(null);
            }
        }
    }

    /**
     * Writes out comment string.
     *
     * @param string   the comment
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     */
    void writeComment(String string) throws IOException {
        write("<!--");
        if (string != null) {
            write(string);
        }
        write("-->");
        writeLineSeparator();
    }

    /**
     * Writes out any additional comments (comments outside of the body)
     * stored under the property HTMLDocument.AdditionalComments.
     */
    void writeAdditionalComments() throws IOException {
        Object comments = getDocument().getProperty(HTMLDocument.AdditionalComments);
        if (comments == null) return; 
        if (comments instanceof Vector) {
            Vector v = (Vector) comments;
            for (int counter = 0, maxCounter = v.size(); counter < maxCounter; counter++) {
                writeComment(v.elementAt(counter).toString());
            }
        }
        //[alex] I've add the following 'else' for single comments:
        else
            writeComment(comments.toString());
        // end add
    }

    /**
     * Returns true if the element is a
     * synthesized element.  Currently we are only testing
     * for the p-implied tag.
     */
    protected boolean synthesizedElement(Element elem) {
        if (matchNameAttribute(elem.getAttributes(), HTML.Tag.IMPLIED)) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if the StyleConstants.NameAttribute is
     * equal to the tag that is passed in as a parameter.
     */
    protected boolean matchNameAttribute(AttributeSet attr, HTML.Tag tag) {
        Object o = attr.getAttribute(StyleConstants.NameAttribute);
        if (o instanceof HTML.Tag) {
            HTML.Tag name = (HTML.Tag) o;
            if (name == tag) {
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for embedded tags in the AttributeSet
     * and writes them out.  It also stores these tags in a vector
     * so that when appropriate the corresponding end tags can be
     * written out.
     *
     * @exception IOException on any I/O error
     */
    protected void writeEmbeddedTags(AttributeSet attr) throws IOException {

        // translate css attributes to html
        attr = convertToHTML(attr, oConvAttr);

        Enumeration names = attr.getAttributeNames();
        while (names.hasMoreElements()) {
            Object name = names.nextElement();
            if (name instanceof HTML.Tag) {
                HTML.Tag tag = (HTML.Tag) name;
                if (tag == HTML.Tag.FORM || tags.contains(tag)) {
                    continue;
                }
                write('<');
                write(tag.toString());
                Object o = attr.getAttribute(tag);
                if (o != null && o instanceof AttributeSet) {
                    writeAttributes((AttributeSet) o);
                }
                write('>');
                tags.addElement(tag);
                tagValues.addElement(o);
            }
        }
    }

    /**
     * Searches the attribute set for a tag, both of which
     * are passed in as a parameter.  Returns true if no match is found
     * and false otherwise.
     */
    private boolean noMatchForTagInAttributes(AttributeSet attr, HTML.Tag t, Object tagValue) {
        if (attr != null && attr.isDefined(t)) {
            Object newValue = attr.getAttribute(t);

            if ((tagValue == null) ? (newValue == null) : (newValue != null && tagValue.equals(newValue))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Searches the attribute set and for each tag
     * that is stored in the tag vector.  If the tag isnt found,
     * then the tag is removed from the vector and a corresponding
     * end tag is written out.
     *
     * @exception IOException on any I/O error
     */
    protected void closeOutUnwantedEmbeddedTags(AttributeSet attr) throws IOException {

        tagsToRemove.removeAllElements();

        // translate css attributes to html
        attr = convertToHTML(attr, null);

        HTML.Tag t;
        Object tValue;
        int firstIndex = -1;
        int size = tags.size();
        // First, find all the tags that need to be removed.
        for (int i = size - 1; i >= 0; i--) {
            t = (HTML.Tag) tags.elementAt(i);
            tValue = tagValues.elementAt(i);
            if ((attr == null) || noMatchForTagInAttributes(attr, t, tValue)) {
                firstIndex = i;
                tagsToRemove.addElement(t);
            }
        }
        if (firstIndex != -1) {
            // Then close them out.
            boolean removeAll = ((size - firstIndex) == tagsToRemove.size());
            for (int i = size - 1; i >= firstIndex; i--) {
                t = (HTML.Tag) tags.elementAt(i);
                if (removeAll || tagsToRemove.contains(t)) {
                    tags.removeElementAt(i);
                    tagValues.removeElementAt(i);
                }
                write('<');
                write('/');
                write(t.toString());
                write('>');
            }
            // Have to output any tags after firstIndex that still remaing,
            // as we closed them out, but they should remain open.
            size = tags.size();
            for (int i = firstIndex; i < size; i++) {
                t = (HTML.Tag) tags.elementAt(i);
                write('<');
                write(t.toString());
                Object o = tagValues.elementAt(i);
                if (o != null && o instanceof AttributeSet) {
                    writeAttributes((AttributeSet) o);
                }
                write('>');
            }
        }
    }

    /**
     * Determines if the element associated with the attributeset
     * is a TEXTAREA or SELECT.  If true, returns true else
     * false
     */
    private boolean isFormElementWithContent(AttributeSet attr) {
        if (matchNameAttribute(attr, HTML.Tag.TEXTAREA) || matchNameAttribute(attr, HTML.Tag.SELECT)) {
            return true;
        }
        return false;
    }

    /**
     * Determines whether a the indentation needs to be
     * incremented.  Basically, if next is a child of current, and
     * next is NOT a synthesized element, the indent level will be
     * incremented.  If there is a parent-child relationship and "next"
     * is a synthesized element, then its children must be indented.
     * This state is maintained by the indentNext boolean.
     *
     * @return boolean that's true if indent level
     *         needs incrementing.
     */
    private boolean indentNext = false;
    private boolean indentNeedsIncrementing(Element current, Element next) {
        if ((next.getParentElement() == current) && !inPre) {
            if (indentNext) {
                indentNext = false;
                return true;
            }
            else if (synthesizedElement(next)) {
                indentNext = true;
            }
            else if (!synthesizedElement(current)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Outputs the maps as elements. Maps are not stored as elements in
     * the document, and as such this is used to output them.
     */
    void writeMaps(Enumeration maps) throws IOException {
        if (maps != null) {
            while (maps.hasMoreElements()) {
                Map map = (Map) maps.nextElement();
                String name = map.getName();

                incrIndent();
                indent();
                write("<map");
                if (name != null) {
                    write(" name=\"");
                    write(name);
                    write("\">");
                }
                else {
                    write('>');
                }
                writeLineSeparator();
                incrIndent();

                // Output the areas
                AttributeSet[] areas = map.getAreas();
                if (areas != null) {
                    for (int counter = 0, maxCounter = areas.length; counter < maxCounter; counter++) {
                        indent();
                        write("<area");
                        writeAttributes(areas[counter]);
                        write("></area>");
                        writeLineSeparator();
                    }
                }
                decrIndent();
                indent();
                write("</map>");
                writeLineSeparator();
                decrIndent();
            }
        }
    }

    /**
     * Outputs the styles as a single element. Styles are not stored as
     * elements, but part of the document. For the time being styles are
     * written out as a comment, inside a style tag.
     */
    void writeStyles(StyleSheet sheet) throws IOException {
        if (sheet != null) {
            Enumeration styles = sheet.getStyleNames();
            if (styles != null) {
                boolean outputStyle = false;
                while (styles.hasMoreElements()) {
                    String name = (String) styles.nextElement();
                    // Don't write out the default style.
                    if (!StyleContext.DEFAULT_STYLE.equals(name)
                        && writeStyle(name, sheet.getStyle(name), outputStyle)) {
                        outputStyle = true;
                    }
                }
                if (outputStyle) {
                    writeStyleEndTag();
                }
            }
        }
    }

    /**
     * Outputs the named style. <code>outputStyle</code> indicates
     * whether or not a style has been output yet. This will return
     * true if a style is written.
     */
    boolean writeStyle(String name, Style style, boolean outputStyle) throws IOException {
        boolean didOutputStyle = false;
        Enumeration attributes = style.getAttributeNames();
        if (attributes != null) {
            while (attributes.hasMoreElements()) {
                Object attribute = attributes.nextElement();
                if (attribute instanceof CSS.Attribute) {
                    String value = style.getAttribute(attribute).toString();
                    if (value != null) {
                        if (!outputStyle) {
                            writeStyleStartTag();
                            outputStyle = true;
                        }
                        if (!didOutputStyle) {
                            didOutputStyle = true;
                            indent();
                            write(name);
                            write(" {");
                        }
                        else {
                            write(";");
                        }
                        write(' ');
                        write(attribute.toString());
                        write(": ");
                        write(value);
                    }
                }
            }
        }
        if (didOutputStyle) {
            write(" }");
            writeLineSeparator();
        }
        return didOutputStyle;
    }

    void writeStyleStartTag() throws IOException {
        indent();
        write("<style type=\"text/css\">");
        incrIndent();
        writeLineSeparator();
        indent();
        write("<!--");
        incrIndent();
        writeLineSeparator();
    }

    void writeStyleEndTag() throws IOException {
        decrIndent();
        indent();
        write("-->");
        writeLineSeparator();
        decrIndent();
        indent();
        write("</style>");
        writeLineSeparator();
        indent();
    }

    // --- conversion support ---------------------------

    /**
     * Convert the give set of attributes to be html for
     * the purpose of writing them out.  Any keys that
     * have been converted will not appear in the resultant
     * set.  Any keys not converted will appear in the
     * resultant set the same as the received set.<p>
     * This will put the converted values into <code>to</code>, unless
     * it is null in which case a temporary AttributeSet will be returned.
     */
    AttributeSet convertToHTML(AttributeSet from, MutableAttributeSet to) {
        if (to == null) {
            to = convAttr;
        }
        to.removeAttributes(to);
        if (writeCSS) {
            convertToHTML40(from, to);
        }
        else {
            convertToHTML32(from, to);
        }
        return to;
    }

    /**
     * If true, the writer will emit CSS attributes in preference
     * to HTML tags/attributes (i.e. It will emit an HTML 4.0
     * style).
     */
    private boolean writeCSS = false;

    /**
     * Buffer for the purpose of attribute conversion
     */
    private MutableAttributeSet convAttr = new SimpleAttributeSet();

    /**
     * Buffer for the purpose of attribute conversion. This can be
     * used if convAttr is being used.
     */
    private MutableAttributeSet oConvAttr = new SimpleAttributeSet();

    /**
     * Create an older style of HTML attributes.  This will
     * convert character level attributes that have a StyleConstants
     * mapping over to an HTML tag/attribute.  Other CSS attributes
     * will be placed in an HTML style attribute.
     */
    private static void convertToHTML32(AttributeSet from, MutableAttributeSet to) {
        if (from == null) {
            return;
        }
        Enumeration keys = from.getAttributeNames();
        String value = "";
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            if (key instanceof CSS.Attribute) {
                if ((key == CSS.Attribute.FONT_FAMILY)
                    || (key == CSS.Attribute.FONT_SIZE)
                    || (key == CSS.Attribute.COLOR)) {

                    createFontAttribute((CSS.Attribute) key, from, to);
                }
                else if (key == CSS.Attribute.FONT_WEIGHT) {
                    // add a bold tag is weight is bold
                    //CSS.FontWeight weightValue = (CSS.FontWeight) from.getAttribute(CSS.Attribute.FONT_WEIGHT);
                    String weightValue = from.getAttribute(CSS.Attribute.FONT_WEIGHT).toString();
                    if (weightValue != null) {
                        int fweight;
                        try {
                            fweight = new Integer(weightValue).intValue();
                        }
                        catch (Exception ex) {
                            fweight = -1;
                        }
                        if ((weightValue.toLowerCase().equals("bold")) || (fweight > 400))
                            to.addAttribute(HTML.Tag.B, SimpleAttributeSet.EMPTY);
                    }
                }
                else if (key == CSS.Attribute.FONT_STYLE) {
                    String s = from.getAttribute(key).toString();
                    if (s.indexOf("italic") >= 0) {
                        to.addAttribute(HTML.Tag.I, SimpleAttributeSet.EMPTY);
                    }
                }
                else if (key == CSS.Attribute.TEXT_DECORATION) {
                    String decor = from.getAttribute(key).toString();
                    if (decor.indexOf("underline") >= 0) {
                        to.addAttribute(HTML.Tag.U, SimpleAttributeSet.EMPTY);
                    }
                    if (decor.indexOf("line-through") >= 0) {
                        to.addAttribute(HTML.Tag.STRIKE, SimpleAttributeSet.EMPTY);
                    }
                }
                else if (key == CSS.Attribute.VERTICAL_ALIGN) {
                    String vAlign = from.getAttribute(key).toString();
                    if (vAlign.indexOf("sup") >= 0) {
                        to.addAttribute(HTML.Tag.SUP, SimpleAttributeSet.EMPTY);
                    }
                    if (vAlign.indexOf("sub") >= 0) {
                        to.addAttribute(HTML.Tag.SUB, SimpleAttributeSet.EMPTY);
                    }
                }
                else if (key == CSS.Attribute.TEXT_ALIGN) {
                    to.addAttribute(HTML.Attribute.ALIGN, from.getAttribute(key).toString());
                }
                else {
                    // default is to store in a HTML style attribute
                    if (value.length() > 0) {
                        value = value + "; ";
                    }
                    value = value + key + ": " + from.getAttribute(key);
                }
            }
            else {
                to.addAttribute(key, from.getAttribute(key));
            }
        }
        if (value.length() > 0) {
            to.addAttribute(HTML.Attribute.STYLE, value);
        }
    }

    /**
     * Create/update an HTML &lt;font&gt; tag attribute.  The
     * value of the attribute should be a MutableAttributeSet so
     * that the attributes can be updated as they are discovered.
     */
    private static void createFontAttribute(CSS.Attribute a, AttributeSet from, MutableAttributeSet to) {
        MutableAttributeSet fontAttr = (MutableAttributeSet) to.getAttribute(HTML.Tag.FONT);
        if (fontAttr == null) {
            fontAttr = new SimpleAttributeSet();
            to.addAttribute(HTML.Tag.FONT, fontAttr);
        }
        // edit the parameters to the font tag
        String htmlValue = from.getAttribute(a).toString();
        if (a == CSS.Attribute.FONT_FAMILY) {
            fontAttr.addAttribute(HTML.Attribute.FACE, htmlValue);
        }
        else if (a == CSS.Attribute.FONT_SIZE) {
            fontAttr.addAttribute(HTML.Attribute.SIZE, htmlValue);
        }
        else if (a == CSS.Attribute.COLOR) {
            fontAttr.addAttribute(HTML.Attribute.COLOR, htmlValue);
        }
    }

    /**
     * Copies the given AttributeSet to a new set, converting
     * any CSS attributes found to arguments of an HTML style
     * attribute.
     */
    private static void convertToHTML40(AttributeSet from, MutableAttributeSet to) {
        Enumeration keys = from.getAttributeNames();
        String value = "";
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            if (key instanceof CSS.Attribute) {
                value = value + " " + key + "=" + from.getAttribute(key) + ";";
            }
            else {
                to.addAttribute(key, from.getAttribute(key));
            }
        }
        if (value.length() > 0) {
            to.addAttribute(HTML.Attribute.STYLE, value);
        }
    }

    //
    // Overrides the writing methods to only break a string when
    // canBreakString is true.
    // In a future release it is likely AbstractWriter will get this
    // functionality.
    //

    /**
     * Writes the line separator. This is overriden to make sure we don't
     * replace the newline content in case it is outside normal ascii.
     */
    protected void writeLineSeparator() throws IOException {
        boolean oldReplace = replaceEntities;
        replaceEntities = false;
        super.writeLineSeparator();
        replaceEntities = oldReplace;
    }

    /**
     * This method is overriden to map any character entities, such as
     * &lt; to &amp;lt;. <code>super.output</code> will be invoked to
     * write the content.
     */
    protected void output(char[] chars, int start, int length) throws IOException {
        if (!replaceEntities) {
            super.output(chars, start, length);
            return;
        }
        int last = start;
        length += start;
        for (int counter = start; counter < length; counter++) {
            // This will change, we need better support character level
            // entities.
            switch (chars[counter]) {
                // Character level entities.
                case '<' :
                    if (counter > last) {
                        super.output(chars, last, counter - last);
                    }
                    last = counter + 1;
                    output("&lt;");
                    break;
                case '>' :
                    if (counter > last) {
                        super.output(chars, last, counter - last);
                    }
                    last = counter + 1;
                    output("&gt;");
                    break;
                case '&' :
                    if (counter > last) {
                        super.output(chars, last, counter - last);
                    }
                    last = counter + 1;
                    output("&amp;");
                    break;
                case '"' :
                    if (counter > last) {
                        super.output(chars, last, counter - last);
                    }
                    last = counter + 1;
                    output("&quot;");
                    break;
                    // Special characters
                case '\n' :
                case '\t' :
                case '\r' :
                    break;
                default :

                    /**
                     * [alex]I've replaced the following line to avoid to substitute non-ascii characters by numeric
                     * values by default. Use _nument=true if you need that substitution
                     * Original "if" condition: if (chars [counter]< ' ' || chars [counter] > 127) 
                     */
                    if ((chars[counter] < ' ') || (_nument && chars[counter] > 127)) {
                        if (counter > last) {
                            super.output(chars, last, counter - last);
                        }
                        last = counter + 1;
                        // If the character is outside of ascii, write the
                        // numeric value.
                        output("&#");
                        output(String.valueOf((int) chars[counter]));
                        output(";");
                    }
                    break;
            }
        }
        if (last < length) {
            super.output(chars, last, length - last);
        }
    }

    /**
     * This directly invokes super's <code>output</code> after converting
     * <code>string</code> to a char[].
     */
    private void output(String string) throws IOException {
        int length = string.length();
        if (tempChars == null || tempChars.length < length) {
            tempChars = new char[length];
        }
        string.getChars(0, length, tempChars, 0);
        super.output(tempChars, 0, length);
    }

    /**
     * This class extends DefaultListModel, and also implements
     * the ListSelectionModel interface, allowing for it to store state
     * relevant to a SELECT form element which is implemented as a List.
     * If SELECT has a size attribute whose value is greater than 1,
     * or if allows multiple selection then a JList is used to 
     * represent it and the OptionListModel is used as its model.
     * It also stores the initial state of the JList, to ensure an
     * accurate reset, if the user requests a reset of the form.
     *
      @author Sunita Mani
      @version 1.9 12/03/01
     */

    class OptionListModel extends DefaultListModel implements ListSelectionModel, Serializable {

        private static final int MIN = -1;
        private static final int MAX = Integer.MAX_VALUE;
        private int selectionMode = SINGLE_SELECTION;
        private int minIndex = MAX;
        private int maxIndex = MIN;
        private int anchorIndex = -1;
        private int leadIndex = -1;
        private int firstChangedIndex = MAX;
        private int lastChangedIndex = MIN;
        private boolean isAdjusting = false;
        private BitSet value = new BitSet(32);
        private BitSet initialValue = new BitSet(32);
        protected EventListenerList listenerList = new EventListenerList();

        protected boolean leadAnchorNotificationEnabled = true;

        public int getMinSelectionIndex() {
            return isSelectionEmpty() ? -1 : minIndex;
        }

        public int getMaxSelectionIndex() {
            return maxIndex;
        }

        public boolean getValueIsAdjusting() {
            return isAdjusting;
        }

        public int getSelectionMode() {
            return selectionMode;
        }

        public void setSelectionMode(int selectionMode) {
            switch (selectionMode) {
                case SINGLE_SELECTION :
                case SINGLE_INTERVAL_SELECTION :
                case MULTIPLE_INTERVAL_SELECTION :
                    this.selectionMode = selectionMode;
                    break;
                default :
                    throw new IllegalArgumentException("invalid selectionMode");
            }
        }

        public boolean isSelectedIndex(int index) {
            return ((index < minIndex) || (index > maxIndex)) ? false : value.get(index);
        }

        public boolean isSelectionEmpty() {
            return (minIndex > maxIndex);
        }

        public void addListSelectionListener(ListSelectionListener l) {
            listenerList.add(ListSelectionListener.class, l);
        }

        public void removeListSelectionListener(ListSelectionListener l) {
            listenerList.remove(ListSelectionListener.class, l);
        }

        /**
         * Returns an array of all the <code>ListSelectionListener</code>s added
         * to this OptionListModel with addListSelectionListener().
         *
         * @return all of the <code>ListSelectionListener</code>s added or an empty
         *         array if no listeners have been added
         * @since 1.4
         */
        public ListSelectionListener[] getListSelectionListeners() {
            return (ListSelectionListener[]) listenerList.getListeners(ListSelectionListener.class);
        }

        /**
         * Notify listeners that we are beginning or ending a
         * series of value changes
         */
        protected void fireValueChanged(boolean isAdjusting) {
            fireValueChanged(getMinSelectionIndex(), getMaxSelectionIndex(), isAdjusting);
        }

        /**
         * Notify ListSelectionListeners that the value of the selection,
         * in the closed interval firstIndex,lastIndex, has changed.
         */
        protected void fireValueChanged(int firstIndex, int lastIndex) {
            fireValueChanged(firstIndex, lastIndex, getValueIsAdjusting());
        }

        /**
         * @param firstIndex The first index in the interval.
         * @param index1 The last index in the interval.
         * @param isAdjusting True if this is the final change in a series of them.
         * @see EventListenerList
         */
        protected void fireValueChanged(int firstIndex, int lastIndex, boolean isAdjusting) {
            Object[] listeners = listenerList.getListenerList();
            ListSelectionEvent e = null;

            for (int i = listeners.length - 2; i >= 0; i -= 2) {
                if (listeners[i] == ListSelectionListener.class) {
                    if (e == null) {
                        e = new ListSelectionEvent(this, firstIndex, lastIndex, isAdjusting);
                    }
                    ((ListSelectionListener) listeners[i + 1]).valueChanged(e);
                }
            }
        }

        private void fireValueChanged() {
            if (lastChangedIndex == MIN) {
                return;
            }
            /* Change the values before sending the event to the
             * listeners in case the event causes a listener to make
             * another change to the selection.
             */
            int oldFirstChangedIndex = firstChangedIndex;
            int oldLastChangedIndex = lastChangedIndex;
            firstChangedIndex = MAX;
            lastChangedIndex = MIN;
            fireValueChanged(oldFirstChangedIndex, oldLastChangedIndex);
        }

        // Update first and last change indices
        private void markAsDirty(int r) {
            firstChangedIndex = Math.min(firstChangedIndex, r);
            lastChangedIndex = Math.max(lastChangedIndex, r);
        }

        // Set the state at this index and update all relevant state.
        private void set(int r) {
            if (value.get(r)) {
                return;
            }
            value.set(r);
            //Option option = (Option) get(r);
            //option.setSelection(true);
            markAsDirty(r);

            // Update minimum and maximum indices
            minIndex = Math.min(minIndex, r);
            maxIndex = Math.max(maxIndex, r);
        }

        // Clear the state at this index and update all relevant state.
        private void clear(int r) {
            if (!value.get(r)) {
                return;
            }
            value.clear(r);
            //Option option = (Option) get(r);
            //option.setSelection(false);
            markAsDirty(r);

            // Update minimum and maximum indices
            /*
               If (r > minIndex) the minimum has not changed.
               The case (r < minIndex) is not possible because r'th value was set.
               We only need to check for the case when lowest entry has been cleared,
               and in this case we need to search for the first value set above it.
            */
            if (r == minIndex) {
                for (minIndex = minIndex + 1; minIndex <= maxIndex; minIndex++) {
                    if (value.get(minIndex)) {
                        break;
                    }
                }
            }
            /*
               If (r < maxIndex) the maximum has not changed.
               The case (r > maxIndex) is not possible because r'th value was set.
               We only need to check for the case when highest entry has been cleared,
               and in this case we need to search for the first value set below it.
            */
            if (r == maxIndex) {
                for (maxIndex = maxIndex - 1; minIndex <= maxIndex; maxIndex--) {
                    if (value.get(maxIndex)) {
                        break;
                    }
                }
            }
            /* Performance note: This method is called from inside a loop in
               changeSelection() but we will only iterate in the loops
               above on the basis of one iteration per deselected cell - in total.
               Ie. the next time this method is called the work of the previous
               deselection will not be repeated.
            
               We also don't need to worry about the case when the min and max
               values are in their unassigned states. This cannot happen because
               this method's initial check ensures that the selection was not empty
               and therefore that the minIndex and maxIndex had 'real' values.
            
               If we have cleared the whole selection, set the minIndex and maxIndex
               to their cannonical values so that the next set command always works
               just by using Math.min and Math.max.
            */
            if (isSelectionEmpty()) {
                minIndex = MAX;
                maxIndex = MIN;
            }
        }

        /**
         * Sets the value of the leadAnchorNotificationEnabled flag.
         * @see     #isLeadAnchorNotificationEnabled()
         */
        public void setLeadAnchorNotificationEnabled(boolean flag) {
            leadAnchorNotificationEnabled = flag;
        }

        /**
         * Returns the value of the leadAnchorNotificationEnabled flag.
         * When leadAnchorNotificationEnabled is true the model
         * generates notification events with bounds that cover all the changes to
         * the selection plus the changes to the lead and anchor indices.
         * Setting the flag to false causes a norrowing of the event's bounds to
         * include only the elements that have been selected or deselected since
         * the last change. Either way, the model continues to maintain the lead
         * and anchor variables internally. The default is true.
         * @return      the value of the leadAnchorNotificationEnabled flag
         * @see     #setLeadAnchorNotificationEnabled(boolean)
         */
        public boolean isLeadAnchorNotificationEnabled() {
            return leadAnchorNotificationEnabled;
        }

        private void updateLeadAnchorIndices(int anchorIndex, int leadIndex) {
            if (leadAnchorNotificationEnabled) {
                if (this.anchorIndex != anchorIndex) {
                    if (this.anchorIndex != -1) { // The unassigned state.
                        markAsDirty(this.anchorIndex);
                    }
                    markAsDirty(anchorIndex);
                }

                if (this.leadIndex != leadIndex) {
                    if (this.leadIndex != -1) { // The unassigned state.
                        markAsDirty(this.leadIndex);
                    }
                    markAsDirty(leadIndex);
                }
            }
            this.anchorIndex = anchorIndex;
            this.leadIndex = leadIndex;
        }

        private boolean contains(int a, int b, int i) {
            return (i >= a) && (i <= b);
        }

        private void changeSelection(int clearMin, int clearMax, int setMin, int setMax, boolean clearFirst) {
            for (int i = Math.min(setMin, clearMin); i <= Math.max(setMax, clearMax); i++) {

                boolean shouldClear = contains(clearMin, clearMax, i);
                boolean shouldSet = contains(setMin, setMax, i);

                if (shouldSet && shouldClear) {
                    if (clearFirst) {
                        shouldClear = false;
                    }
                    else {
                        shouldSet = false;
                    }
                }

                if (shouldSet) {
                    set(i);
                }
                if (shouldClear) {
                    clear(i);
                }
            }
            fireValueChanged();
        }

        /*   Change the selection with the effect of first clearing the values
         *   in the inclusive range [clearMin, clearMax] then setting the values
         *   in the inclusive range [setMin, setMax]. Do this in one pass so
         *   that no values are cleared if they would later be set.
         */
        private void changeSelection(int clearMin, int clearMax, int setMin, int setMax) {
            changeSelection(clearMin, clearMax, setMin, setMax, true);
        }

        public void clearSelection() {
            removeSelectionInterval(minIndex, maxIndex);
        }

        public void setSelectionInterval(int index0, int index1) {
            if (index0 == -1 || index1 == -1) {
                return;
            }

            if (getSelectionMode() == SINGLE_SELECTION) {
                index0 = index1;
            }

            updateLeadAnchorIndices(index0, index1);

            int clearMin = minIndex;
            int clearMax = maxIndex;
            int setMin = Math.min(index0, index1);
            int setMax = Math.max(index0, index1);
            changeSelection(clearMin, clearMax, setMin, setMax);
        }

        public void addSelectionInterval(int index0, int index1) {
            if (index0 == -1 || index1 == -1) {
                return;
            }

            if (getSelectionMode() != MULTIPLE_INTERVAL_SELECTION) {
                setSelectionInterval(index0, index1);
                return;
            }

            updateLeadAnchorIndices(index0, index1);

            int clearMin = MAX;
            int clearMax = MIN;
            int setMin = Math.min(index0, index1);
            int setMax = Math.max(index0, index1);
            changeSelection(clearMin, clearMax, setMin, setMax);
        }

        public void removeSelectionInterval(int index0, int index1) {
            if (index0 == -1 || index1 == -1) {
                return;
            }

            updateLeadAnchorIndices(index0, index1);

            int clearMin = Math.min(index0, index1);
            int clearMax = Math.max(index0, index1);
            int setMin = MAX;
            int setMax = MIN;
            changeSelection(clearMin, clearMax, setMin, setMax);
        }

        private void setState(int index, boolean state) {
            if (state) {
                set(index);
            }
            else {
                clear(index);
            }
        }

        /**
         * Insert length indices beginning before/after index. If the value 
         * at index is itself selected, set all of the newly inserted 
         * items, otherwise leave them unselected. This method is typically
         * called to sync the selection model with a corresponding change
         * in the data model.
         */
        public void insertIndexInterval(int index, int length, boolean before) {
            /* The first new index will appear at insMinIndex and the last
             * one will appear at insMaxIndex
             */
            int insMinIndex = (before) ? index : index + 1;
            int insMaxIndex = (insMinIndex + length) - 1;

            /* Right shift the entire bitset by length, beginning with
             * index-1 if before is true, index+1 if it's false (i.e. with
             * insMinIndex).
             */
            for (int i = maxIndex; i >= insMinIndex; i--) {
                setState(i + length, value.get(i));
            }

            /* Initialize the newly inserted indices.
             */
            boolean setInsertedValues = value.get(index);
            for (int i = insMinIndex; i <= insMaxIndex; i++) {
                setState(i, setInsertedValues);
            }
        }

        /**
         * Remove the indices in the interval index0,index1 (inclusive) from
         * the selection model.  This is typically called to sync the selection
         * model width a corresponding change in the data model.  Note
         * that (as always) index0 can be greater than index1.
         */
        public void removeIndexInterval(int index0, int index1) {
            int rmMinIndex = Math.min(index0, index1);
            int rmMaxIndex = Math.max(index0, index1);
            int gapLength = (rmMaxIndex - rmMinIndex) + 1;

            /* Shift the entire bitset to the left to close the index0, index1
             * gap.
             */
            for (int i = rmMinIndex; i <= maxIndex; i++) {
                setState(i, value.get(i + gapLength));
            }
        }

        public void setValueIsAdjusting(boolean isAdjusting) {
            if (isAdjusting != this.isAdjusting) {
                this.isAdjusting = isAdjusting;
                this.fireValueChanged(isAdjusting);
            }
        }

        public String toString() {
            String s = ((getValueIsAdjusting()) ? "~" : "=") + value.toString();
            return getClass().getName() + " " + Integer.toString(hashCode()) + " " + s;
        }

        /**
         * Returns a clone of the receiver with the same selection.
         * <code>listenerLists</code> are not duplicated.
         *
         * @return a clone of the receiver
         * @exception CloneNotSupportedException if the receiver does not
         *    both (a) implement the <code>Cloneable</code> interface
         *    and (b) define a <code>clone</code> method
         */
        public Object clone() throws CloneNotSupportedException {
            OptionListModel clone = (OptionListModel) super.clone();
            clone.value = (BitSet) value.clone();
            clone.listenerList = new EventListenerList();
            return clone;
        }

        public int getAnchorSelectionIndex() {
            return anchorIndex;
        }

        public int getLeadSelectionIndex() {
            return leadIndex;
        }

        /**
         * Set the anchor selection index, leaving all selection values unchanged. 
         *
         * @see #getAnchorSelectionIndex     
         * @see #setLeadSelectionIndex
         */
        public void setAnchorSelectionIndex(int anchorIndex) {
            this.anchorIndex = anchorIndex;
        }

        /**
         * Set the lead selection index, ensuring that values between the 
         * anchor and the new lead are either all selected or all deselected. 
         * If the value at the anchor index is selected, first clear all the 
         * values in the range [anchor, oldLeadIndex], then select all the values 
         * values in the range [anchor, newLeadIndex], where oldLeadIndex is the old 
         * leadIndex and newLeadIndex is the new one. 
         * <p> 
         * If the value at the anchor index is not selected, do the same thing in reverse, 
         * selecting values in the old range and deslecting values in the new one. 
         * <p>
         * Generate a single event for this change and notify all listeners. 
         * For the purposes of generating minimal bounds in this event, do the 
         * operation in a single pass; that way the first and last index inside the 
         * ListSelectionEvent that is broadcast will refer to cells that actually 
         * changed value because of this method. If, instead, this operation were 
         * done in two steps the effect on the selection state would be the same 
         * but two events would be generated and the bounds around the changed values 
         * would be wider, including cells that had been first cleared and only 
         * to later be set. 
         * <p>
         * This method can be used in the mouseDragged() method of a UI class 
         * to extend a selection.  
         *
         * @see #getLeadSelectionIndex     
         * @see #setAnchorSelectionIndex
         */
        public void setLeadSelectionIndex(int leadIndex) {
            int anchorIndex = this.anchorIndex;
            if (getSelectionMode() == SINGLE_SELECTION) {
                anchorIndex = leadIndex;
            }

            int oldMin = Math.min(this.anchorIndex, this.leadIndex);
            ;
            int oldMax = Math.max(this.anchorIndex, this.leadIndex);
            ;
            int newMin = Math.min(anchorIndex, leadIndex);
            int newMax = Math.max(anchorIndex, leadIndex);
            if (value.get(this.anchorIndex)) {
                changeSelection(oldMin, oldMax, newMin, newMax);
            }
            else {
                changeSelection(newMin, newMax, oldMin, oldMax, false);
            }
            this.anchorIndex = anchorIndex;
            this.leadIndex = leadIndex;
        }

        /**
         * This method is responsible for storing the state
         * of the initial selection.  If the selectionMode
         * is the default, i.e allowing only for SINGLE_SELECTION,
         * then the very last OPTION that has the selected
         * attribute set wins.
         */
        public void setInitialSelection(int i) {
            if (initialValue.get(i)) {
                return;
            }
            if (selectionMode == SINGLE_SELECTION) {
                // reset to empty
                initialValue.and(new BitSet());
            }
            initialValue.set(i);
        }

        /**
         * Fetches the BitSet that represents the initial
         * set of selected items in the list.
         */
        public BitSet getInitialSelection() {
            return initialValue;
        }
    }

    class OptionComboBoxModel extends DefaultComboBoxModel implements Serializable {

        private Option selectedOption = null;

        /**
         * Stores the Option that has been marked its
         * selected attribute set.
         */
        public void setInitialSelection(Option option) {
            selectedOption = option;
        }

        /**
         * Fetches the Option item that represents that was
         * initially set to a selected state.
         */
        public Option getInitialSelection() {
            return selectedOption;
        }
    }

    /**
     * Map is used to represent a map element that is part of an HTML document.
     * Once a Map has been created, and any number of areas have been added,
     * you can test if a point falls inside the map via the contains method.
     *
     * @author  Scott Violet
     * @version 1.6 12/03/01
     */
    class Map {
        /** Name of the Map. */
        private String name;
        /** An array of AttributeSets. */
        private Vector areaAttributes;
        /** An array of RegionContainments, will slowly grow to match the
         * length of areaAttributes as needed. */
        private Vector areas;

        public Map() {}

        public Map(String name) {
            this.name = name;
        }

        /**
         * Returns the name of the Map.
         */
        public String getName() {
            return name;
        }

        /**
         * Defines a region of the Map, based on the passed in AttributeSet.
         */
        public void addArea(AttributeSet as) {
            if (as == null) {
                return;
            }
            if (areaAttributes == null) {
                areaAttributes = new Vector(2);
            }
            areaAttributes.addElement(as.copyAttributes());
        }

        /**
         * Removes the previously created area.
         */
        public void removeArea(AttributeSet as) {
            if (as != null && areaAttributes != null) {
                int numAreas = (areas != null) ? areas.size() : 0;
                for (int counter = areaAttributes.size() - 1; counter >= 0; counter--) {
                    if (((AttributeSet) areaAttributes.elementAt(counter)).isEqual(as)) {
                        areaAttributes.removeElementAt(counter);
                        if (counter < numAreas) {
                            areas.removeElementAt(counter);
                        }
                    }
                }
            }
        }

        /**
         * Returns the AttributeSets representing the differet areas of the Map.
         */
        public AttributeSet[] getAreas() {
            int numAttributes = (areaAttributes != null) ? areaAttributes.size() : 0;
            if (numAttributes != 0) {
                AttributeSet[] retValue = new AttributeSet[numAttributes];

                areaAttributes.copyInto(retValue);
                return retValue;
            }
            return null;
        }

        /**
         * Returns the AttributeSet that contains the passed in location,
         * <code>x</code>, <code>y</code>. <code>width</code>, <code>height</code>
         * gives the size of the region the map is defined over. If a matching
         * area is found, the AttribueSet for it is returned.
         */
        public AttributeSet getArea(int x, int y, int width, int height) {
            int numAttributes = (areaAttributes != null) ? areaAttributes.size() : 0;

            if (numAttributes > 0) {
                int numAreas = (areas != null) ? areas.size() : 0;

                if (areas == null) {
                    areas = new Vector(numAttributes);
                }
                for (int counter = 0; counter < numAttributes; counter++) {
                    if (counter >= numAreas) {
                        areas.addElement(createRegionContainment((AttributeSet) areaAttributes.elementAt(counter)));
                    }
                    RegionContainment rc = (RegionContainment) areas.elementAt(counter);
                    if (rc != null && rc.contains(x, y, width, height)) {
                        return (AttributeSet) areaAttributes.elementAt(counter);
                    }
                }
            }
            return null;
        }

        /**
         * Creates and returns an instance of RegionContainment that can be
         * used to test if a particular point lies inside a region.
         */
        protected RegionContainment createRegionContainment(AttributeSet attributes) {
            Object shape = attributes.getAttribute(HTML.Attribute.SHAPE);

            if (shape == null) {
                shape = "rect";
            }
            if (shape instanceof String) {
                String shapeString = ((String) shape).toLowerCase();
                RegionContainment rc = null;

                try {
                    if (shapeString.equals("rect")) {
                        rc = new RectangleRegionContainment(attributes);
                    }
                    else if (shapeString.equals("circle")) {
                        rc = new CircleRegionContainment(attributes);
                    }
                    else if (shapeString.equals("poly")) {
                        rc = new PolygonRegionContainment(attributes);
                    }
                    else if (shapeString.equals("default")) {
                        rc = DefaultRegionContainment.sharedInstance();
                    }
                }
                catch (RuntimeException re) {
                    // Something wrong with attributes.
                    rc = null;
                }
                return rc;
            }
            return null;
        }

    }

    /**
     * Used to test for containment in a circular region.
     */
    static class CircleRegionContainment implements RegionContainment {
        /** X origin of the circle. */
        int x;
        /** Y origin of the circle. */
        int y;
        /** Radius of the circle. */
        int radiusSquared;
        /** Non-null indicates one of the values represents a percent. */
        float[] percentValues;
        /** Last value of width passed in. */
        int lastWidth;
        /** Last value of height passed in. */
        int lastHeight;

        public CircleRegionContainment(AttributeSet as) {
            int[] coords = extractCoords(as.getAttribute(HTML.Attribute.COORDS));

            if (coords == null || coords.length != 3) {
                throw new RuntimeException("Unable to parse circular area");
            }
            x = coords[0];
            y = coords[1];
            radiusSquared = coords[2] * coords[2];
            if (coords[0] < 0 || coords[1] < 0 || coords[2] < 0) {
                lastWidth = lastHeight = -1;
                percentValues = new float[3];
                for (int counter = 0; counter < 3; counter++) {
                    if (coords[counter] < 0) {
                        percentValues[counter] = coords[counter] / -100.0f;
                    }
                    else {
                        percentValues[counter] = -1.0f;
                    }
                }
            }
            else {
                percentValues = null;
            }
        }

        public boolean contains(int x, int y, int width, int height) {
            if (percentValues != null && (lastWidth != width || lastHeight != height)) {
                // int newRad = Math.min(width, height) / 2;

                lastWidth = width;
                lastHeight = height;
                if (percentValues[0] != -1.0f) {
                    this.x = (int) (percentValues[0] * width);
                }
                if (percentValues[1] != -1.0f) {
                    this.y = (int) (percentValues[1] * height);
                }
                if (percentValues[2] != -1.0f) {
                    radiusSquared = (int) (percentValues[2] * Math.min(width, height));
                    radiusSquared *= radiusSquared;
                }
            }
            return (((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y)) <= radiusSquared);
        }
    }

    /**
     * An implementation that will return true if the x, y location is
     * inside a rectangle defined by origin 0, 0, and width equal to
     * width passed in, and height equal to height passed in.
     */
    static class DefaultRegionContainment implements RegionContainment {
        /** A global shared instance. */
        static DefaultRegionContainment si = null;

        public static DefaultRegionContainment sharedInstance() {
            if (si == null) {
                si = new DefaultRegionContainment();
            }
            return si;
        }

        public boolean contains(int x, int y, int width, int height) {
            return (x <= width && x >= 0 && y >= 0 && y <= width);
        }
    }

    /**
     * Used to test for containment in a polygon region.
     */
    static class PolygonRegionContainment extends Polygon implements RegionContainment {
        /** If any value is a percent there will be an entry here for the
         * percent value. Use percentIndex to find out the index for it. */
        float[] percentValues;
        int[] percentIndexs;
        /** Last value of width passed in. */
        int lastWidth;
        /** Last value of height passed in. */
        int lastHeight;

        public PolygonRegionContainment(AttributeSet as) {
            int[] coords = extractCoords(as.getAttribute(HTML.Attribute.COORDS));

            if (coords == null || coords.length == 0 || coords.length % 2 != 0) {
                throw new RuntimeException("Unable to parse polygon area");
            }
            else {
                int numPercents = 0;

                lastWidth = lastHeight = -1;
                for (int counter = coords.length - 1; counter >= 0; counter--) {
                    if (coords[counter] < 0) {
                        numPercents++;
                    }
                }

                if (numPercents > 0) {
                    percentIndexs = new int[numPercents];
                    percentValues = new float[numPercents];
                    for (int counter = coords.length - 1, pCounter = 0; counter >= 0; counter--) {
                        if (coords[counter] < 0) {
                            percentValues[pCounter] = coords[counter] / -100.0f;
                            percentIndexs[pCounter] = counter;
                            pCounter++;
                        }
                    }
                }
                else {
                    percentIndexs = null;
                    percentValues = null;
                }
                npoints = coords.length / 2;
                xpoints = new int[npoints];
                ypoints = new int[npoints];

                for (int counter = 0; counter < npoints; counter++) {
                    xpoints[counter] = coords[counter + counter];
                    ypoints[counter] = coords[counter + counter + 1];
                }
            }
        }

        public boolean contains(int x, int y, int width, int height) {
            if (percentValues == null || (lastWidth == width && lastHeight == height)) {
                return contains(x, y);
            }
            // Force the bounding box to be recalced.
            bounds = null;
            lastWidth = width;
            lastHeight = height;
            float fWidth = (float) width;
            float fHeight = (float) height;
            for (int counter = percentValues.length - 1; counter >= 0; counter--) {
                if (percentIndexs[counter] % 2 == 0) {
                    // x
                    xpoints[percentIndexs[counter] / 2] = (int) (percentValues[counter] * fWidth);
                }
                else {
                    // y
                    ypoints[percentIndexs[counter] / 2] = (int) (percentValues[counter] * fHeight);
                }
            }
            return contains(x, y);
        }
    }

    /**
     * Used to test for containment in a rectangular region.
     */
    static class RectangleRegionContainment implements RegionContainment {
        /** Will be non-null if one of the values is a percent, and any value
         * that is non null indicates it is a percent
         * (order is x, y, width, height). */
        float[] percents;
        /** Last value of width passed in. */
        int lastWidth;
        /** Last value of height passed in. */
        int lastHeight;
        /** Top left. */
        int x0;
        int y0;
        /** Bottom right. */
        int x1;
        int y1;

        public RectangleRegionContainment(AttributeSet as) {
            int[] coords = extractCoords(as.getAttribute(HTML.Attribute.COORDS));

            percents = null;
            if (coords == null || coords.length != 4) {
                throw new RuntimeException("Unable to parse rectangular area");
            }
            else {
                x0 = coords[0];
                y0 = coords[1];
                x1 = coords[2];
                y1 = coords[3];
                if (x0 < 0 || y0 < 0 || x1 < 0 || y1 < 0) {
                    percents = new float[4];
                    lastWidth = lastHeight = -1;
                    for (int counter = 0; counter < 4; counter++) {
                        if (coords[counter] < 0) {
                            percents[counter] = Math.abs(coords[counter]) / 100.0f;
                        }
                        else {
                            percents[counter] = -1.0f;
                        }
                    }
                }
            }
        }

        public boolean contains(int x, int y, int width, int height) {
            if (percents == null) {
                return contains(x, y);
            }
            if (lastWidth != width || lastHeight != height) {
                lastWidth = width;
                lastHeight = height;
                if (percents[0] != -1.0f) {
                    x0 = (int) (percents[0] * width);
                }
                if (percents[1] != -1.0f) {
                    y0 = (int) (percents[1] * height);
                }
                if (percents[2] != -1.0f) {
                    x1 = (int) (percents[2] * width);
                }
                if (percents[3] != -1.0f) {
                    y1 = (int) (percents[3] * height);
                }
            }
            return contains(x, y);
        }

        public boolean contains(int x, int y) {
            return ((x >= x0 && x <= x1) && (y >= y0 && y <= y1));
        }
    }

    /**
     * Creates and returns an array of integers from the String
     * <code>stringCoords</code>. If one of the values represents a
     * % the returned value with be negative. If a parse error results
     * from trying to parse one of the numbers null is returned.
     */
    static protected int[] extractCoords(Object stringCoords) {
        if (stringCoords == null || !(stringCoords instanceof String)) {
            return null;
        }

        StringTokenizer st = new StringTokenizer((String) stringCoords, ", \t\n\r");
        int[] retValue = null;
        int numCoords = 0;

        while (st.hasMoreElements()) {
            String token = st.nextToken();
            int scale;

            if (token.endsWith("%")) {
                scale = -1;
                token = token.substring(0, token.length() - 1);
            }
            else {
                scale = 1;
            }
            try {
                int intValue = Integer.parseInt(token);

                if (retValue == null) {
                    retValue = new int[4];
                }
                else if (numCoords == retValue.length) {
                    int[] temp = new int[retValue.length * 2];

                    System.arraycopy(retValue, 0, temp, 0, retValue.length);
                    retValue = temp;
                }
                retValue[numCoords++] = intValue * scale;
            }
            catch (NumberFormatException nfe) {
                return null;
            }
        }
        if (numCoords > 0 && numCoords != retValue.length) {
            int[] temp = new int[numCoords];

            System.arraycopy(retValue, 0, temp, 0, numCoords);
            retValue = temp;
        }
        return retValue;
    }

    /**
        * Defines the interface used for to check if a point is inside a
        * region.
        */
    interface RegionContainment {
        /**
         * Returns true if the location <code>x</code>, <code>y</code>
         * falls inside the region defined in the receiver.
         * <code>width</code>, <code>height</code> is the size of
         * the enclosing region.
         */
        public boolean contains(int x, int y, int width, int height);
    }

}
