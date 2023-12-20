<?xml version="1.0"?>
<!DOCTYPE xsl:stylesheet
[
  <!ENTITY upper 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'>
  <!ENTITY lower 'abcdefghijklmnopqrstuvwxyz'>
  <!ENTITY oasis-spec 'https://docs.oasis-open.org/templates/DocBook/spec-0.8/'>
  <!ENTITY separator " ">
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:saxon="http://icl.com/saxon"
                xmlns:lxslt="http://xml.apache.org/xslt"
                xmlns:xalanredirect="org.apache.xalan.xslt.extensions.Redirect"
                xmlns:exsl="http://exslt.org/common"
                extension-element-prefixes="saxon xalanredirect lxslt exsl"
                version="1.0">
<!-- $Id: oasis-note-html.xsl,v 1.14 2020/03/28 15:11:10 admin Exp $ -->

<!-- This stylesheet is a customization of the DocBook XSL Stylesheets -->
<!-- from http://docs.oasis-open.org/templates/ -->
<!-- See http://sourceforge.net/projects/docbook/ -->
<xsl:import href="../docbook/xsl/html/docbook.xsl"/>
<xsl:include href="titlepage-notes-html.xsl"/>
<xsl:include href="oasis-mathml-html.xsl"/>

<!-- ============================================================ -->
<!-- Parameters -->

<!--online configuration-->
<xsl:param name="css.path"
           select="'&oasis-spec;css/'"/>
<xsl:param name="oasis.logo"
           select="'&oasis-spec;OASISLogo.png'"/>
<xsl:param name="oasis-base" select="'no'"/>

<!--common between offline and online-->

<xsl:param name="css.stylesheet">oasis-note.css</xsl:param>
<!--No longer changing the style based on the stage of development
  <xsl:choose>
    <xsl:when test="/article/@status='Working Draft'">oasis-wd.css</xsl:when>
    <xsl:when test="/article/@status=&quot;Editor's Draft&quot;">oasis-ed.css</xsl:when>
    <xsl:when test="/article/@status='Committee Draft'">oasis-cd.css</xsl:when>
    <xsl:when test="/article/@status='Committee Specification'">oasis-cs.css</xsl:when>
    <xsl:when test="/article/@status='Standard'">oasis-standard.css</xsl:when>
    <xsl:otherwise>
      <xsl:message>
        <xsl:text>Unrecognized status: '</xsl:text>
        <xsl:value-of select="/article/@status"/>
        <xsl:text>'; styling as Working Draft.</xsl:text>
      </xsl:message>
      <xsl:text>oasis-wd.css</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:param>
-->

<xsl:param name="html.stylesheet">
  <xsl:value-of select="$css.path"/>
  <xsl:value-of select="$css.stylesheet"/>
</xsl:param>

<xsl:param name="section.autolabel" select="'1'"/>
<xsl:param name="section.label.includes.component.label" select="1"/>
<xsl:param name="toc.section.depth">3</xsl:param>

<xsl:param name="generate.component.toc" select="'1'"/>

<xsl:param name="method" select="'html'"/>
<xsl:param name="indent" select="'no'"/>
<xsl:param name="encoding" select="'UTF-8'"/>
<xsl:param name="automatic-output-filename" select="'no'"/>

<!-- ============================================================ -->
<!-- Filtering unexpected content -->
<xsl:template match="*[normalize-space(@condition) and
                       not(contains(@condition,'oasis'))]" priority="100">
  <!--not for this process-->
</xsl:template>
  
<xsl:template match="*[normalize-space(@condition) and
                       not(contains(@condition,'oasis'))]" priority="100"
              mode="titlepage.mode">
  <!--not for this process-->
</xsl:template>
  
<!-- ============================================================ -->
<!-- The document -->
<xsl:template match="/">
  <xsl:variable name="content">
    <xsl:apply-imports/>
  </xsl:variable>

  <xsl:variable name="filename">
    <xsl:value-of select="/article/articleinfo/productname[1]"/>
    <xsl:if test="/article/articleinfo/productnumber">
      <xsl:text>-</xsl:text>
      <xsl:value-of select="/article/articleinfo/productnumber[1]"/>
    </xsl:if>
    <xsl:text>.html</xsl:text>
  </xsl:variable>

  <xsl:choose>
    <xsl:when test="$automatic-output-filename!='yes' or
                    not(normalize-space($filename))">
      <xsl:copy-of select="$content"/>      
    </xsl:when>
    <xsl:when test="element-available('exsl:document')">
      <xsl:message>Writing <xsl:value-of select="$filename"/></xsl:message>
      <exsl:document href="{$filename}"
                     method="{$method}"
                     encoding="{$encoding}"
                     indent="{$indent}">
        <xsl:copy-of select="$content"/>
      </exsl:document>
    </xsl:when>
    <xsl:when test="element-available('saxon:output')">
      <xsl:message>Writing <xsl:value-of select="$filename"/></xsl:message>
      <saxon:output href="{$filename}"
                    method="{$method}"
                    encoding="{$encoding}"
                    indent="{$indent}">
        <xsl:copy-of select="$content"/>
      </saxon:output>
    </xsl:when>
    <xsl:when test="element-available('xalanredirect:write')">
      <!-- Xalan uses xalanredirect -->
      <xsl:message>Writing <xsl:value-of select="$filename"/></xsl:message>
      <xalanredirect:write file="{$filename}">
        <xsl:copy-of select="$content"/>
      </xalanredirect:write>
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy-of select="$content"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- ============================================================ -->
<!-- HTML META -->

<xsl:template name="user.head.content">
  <xsl:param name="node" select="."/>
  <xsl:if test="$oasis-base='yes'">
    <xsl:for-each select="/*/articleinfo/releaseinfo
                          [starts-with(@role,'OASIS-specification-this')]
                          [contains(.,'.htm')]">
      <base href="{.}"/>
    </xsl:for-each>
  </xsl:if>
  <xsl:apply-templates select="/*/articleinfo/releaseinfo[@role='cvs']"
                       mode="head.meta.content"/>
  <xsl:call-template name="oasis.head.mathml"/>
</xsl:template>

<xsl:template match="releaseinfo" mode="head.meta.content">
  <meta name="cvsinfo">
    <xsl:attribute name="content">
      <xsl:value-of select="substring-before(substring-after(.,'$'),'$')"/>
    </xsl:attribute>
  </meta>
</xsl:template>

<!-- ============================================================ -->
<!-- Titlepage -->

<xsl:template match="articleinfo/title" mode="titlepage.mode">
  <div style="font-size:200%;font-weight:bold;color:#7f7f7f;margin-top:0pt"
    >OASIS Committee Note</div>
  <hr style="margin-bottom:0pt"/>
  <h1>
    <xsl:apply-templates/>
  </h1>
</xsl:template>

<xsl:template match="pubdate" mode="titlepage.mode">
  <h2>
    <xsl:choose>
      <xsl:when test="/*/@status">
        <xsl:call-template name="split-status">
          <xsl:with-param name="rest" select="/*/@status"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>???Unknown Status???</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </h2>

  <h2>
    <xsl:apply-templates mode="titlepage.mode"/>
  </h2>
</xsl:template>
  
<xsl:template name="split-status">
  <xsl:param name="rest"/>
  <xsl:choose>
    <xsl:when test="contains($rest,'/')">
      <xsl:value-of select="substring-before($rest,'/')"/>/<xsl:text/>
      <br/>
      <xsl:call-template name="split-status">
        <xsl:with-param name="rest">
          <xsl:value-of select="substring-after($rest,'/')"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="$rest"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="productname" mode="titlepage.mode">
<!--
  <xsl:variable name="pn" select="../productnumber[1]"/>

  <dl>
    <dt><span class="docid-heading">Document identifier:</span></dt>
    <dd>
        <xsl:value-of select="."/>
        <xsl:if test="$pn">
          <xsl:text>-</xsl:text>
          <xsl:value-of select="$pn"/>
        </xsl:if>
        <xsl:if test="../releaseinfo[@role='product']">
          <xsl:text> (</xsl:text>
          <xsl:for-each select="../releaseinfo[@role='product']">
            <xsl:if test="position() &gt; 1">, </xsl:if>
            <xsl:apply-templates select="." mode="product.mode"/>
          </xsl:for-each>
          <xsl:text>)</xsl:text>
        </xsl:if>
    </dd>
  </dl>
-->
</xsl:template>

<xsl:template match="releaseinfo[@role='product']" mode="titlepage.mode" priority="2">
  <!-- suppress -->
</xsl:template>

<xsl:template match="releaseinfo[@role='product']" mode="product.mode" priority="2">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="releaseinfo[@role='committee']" mode="titlepage.mode" priority="2">
  <dl>
    <dt><span class="loc-heading">Technical Committee:</span></dt>
    <dd>
        <xsl:apply-templates/>
    </dd>
  </dl>
</xsl:template>

<xsl:template match="releaseinfo[@role='oasis-id']" mode="titlepage.mode" priority="2">
  <dl>
    <dt><span class="loc-heading">OASIS identifier:</span></dt>
    <dd>
        <xsl:apply-templates/>
    </dd>
  </dl>
</xsl:template>

<xsl:template match="releaseinfo[starts-with(@role,'OASIS-specification-')]"
              mode="titlepage.mode" priority="2">
  <xsl:if test="not(preceding-sibling::releaseinfo
                                 [starts-with(@role,'OASIS-specification-')])">
    <xsl:variable name="locations" 
                  select="../releaseinfo[starts-with(@role,
                                         'OASIS-specification-')]"/>
    <xsl:call-template name="spec-uri-group">
      <xsl:with-param name="header">This stage:</xsl:with-param>
      <xsl:with-param name="uris" 
           select="$locations[starts-with(@role,'OASIS-specification-this')]"/>
    </xsl:call-template>
    <xsl:call-template name="spec-uri-group">
      <xsl:with-param name="header">Previous stage:</xsl:with-param>
      <xsl:with-param name="uris" 
       select="$locations[starts-with(@role,'OASIS-specification-previous')]"/>
    </xsl:call-template>
    <xsl:call-template name="spec-uri-group">
      <xsl:with-param name="header">Latest stage:</xsl:with-param>
      <xsl:with-param name="uris" 
         select="$locations[starts-with(@role,'OASIS-specification-latest')]"/>
    </xsl:call-template>
  </xsl:if>
</xsl:template>

<xsl:template name="spec-uri-group">
  <xsl:param name="header"/>
  <xsl:param name="uris"/>
  <dl>
    <dt>
      <span class="loc-heading">
        <xsl:copy-of select="$header"/>
      </span>
    </dt>
    <dd>
      <xsl:choose>
        <xsl:when test="not($uris)">
          N/A
        </xsl:when>
        <xsl:otherwise>
          <xsl:for-each select="$uris">
            <xsl:choose>
              <xsl:when test="contains(@role,'-draft')">
                <xsl:apply-templates/>
              </xsl:when>
              <xsl:otherwise>
                <a href="{.}">
                  <xsl:value-of select="."/>
                </a>
                <xsl:if test="contains(@role,'-authoritative')">
                  (Authoritative)
                </xsl:if>
              </xsl:otherwise>
            </xsl:choose>
            <br/>
          </xsl:for-each>
        </xsl:otherwise>
      </xsl:choose>
    </dd>
  </dl>
</xsl:template>

<xsl:template match="authorgroup" mode="titlepage.mode">
  <xsl:variable name="chairs"  select="othercredit"/>
  <xsl:variable name="editors" select="editor"/>
  <xsl:variable name="authors" select="author"/>

  <xsl:if test="$chairs">
    <dl>
      <dt>
        <span class="contrib-heading">
          <xsl:text>Chair</xsl:text>
          <xsl:if test="count($chairs) &gt; 1">s</xsl:if>
          <xsl:text>:</xsl:text>
        </span>
      </dt>
      <dd>
          <xsl:apply-templates select="$chairs" mode="titlepage.mode"/>
      </dd>
    </dl>
  </xsl:if>
  <xsl:if test="$editors">
    <dl>
      <dt>
        <span class="editor-heading">
          <xsl:text>Editor</xsl:text>
          <xsl:if test="count($editors) &gt; 1">s</xsl:if>
          <xsl:text>:</xsl:text>
        </span>
      </dt>
      <dd>
          <xsl:apply-templates select="$editors" mode="titlepage.mode"/>
      </dd>
    </dl>
  </xsl:if>

  <xsl:if test="$authors">
    <dl>
      <dt>
        <span class="author-heading">
          <xsl:text>Author</xsl:text>
          <xsl:if test="count($authors) &gt; 1">s</xsl:if>
          <xsl:text>:</xsl:text>
        </span>
      </dt>
      <dd>
          <xsl:apply-templates select="$authors" mode="titlepage.mode"/>
      </dd>
    </dl>
  </xsl:if>

</xsl:template>

<xsl:template match="releaseinfo[@role='subject-keywords']" mode="titlepage.mode" priority="2">
  <dl>
    <dt><span class="loc-heading">Subject / Keywords:</span></dt>
    <dd>
        <xsl:apply-templates/>
    </dd>
  </dl>
</xsl:template>

<xsl:template match="releaseinfo[@role='topic']" mode="titlepage.mode" priority="2">
  <dl>
    <dt><span class="loc-heading">OASIS Conceptual Model Topic Area:</span></dt>
    <dd>
        <xsl:apply-templates/>
    </dd>
  </dl>
</xsl:template>

<xsl:template match="ulink" mode="revision-links">
  <xsl:if test="position() = 1"> (</xsl:if>
  <xsl:if test="position() &gt; 1">, </xsl:if>
  <a href="{@url}"><xsl:value-of select="@role"/></a>
  <xsl:if test="position() = last()">)</xsl:if>
</xsl:template>

<xsl:template match="editor|author|othercredit" mode="titlepage.mode">
  <xsl:call-template name="person.name"/>
  <xsl:if test="contrib">
    <xsl:text> (</xsl:text>
    <xsl:apply-templates select="contrib" mode="titlepage.mode"/>
    <xsl:text>)</xsl:text>
  </xsl:if>
  <xsl:apply-templates select="email"
                       mode="titlepage.mode"/>
  <xsl:if test="affiliation/orgname">
    <xsl:text>, </xsl:text>
    <xsl:apply-templates select="affiliation/orgname" mode="titlepage.mode"/>
  </xsl:if>
  <xsl:if test="position()&lt;last()"><br/></xsl:if>
</xsl:template>

<xsl:template match="email" mode="titlepage.mode">
  <xsl:text>&separator;(</xsl:text>
  <a href="mailto:{.}">
    <xsl:apply-templates/>
  </a>
  <xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="abstract" mode="titlepage.mode">
  <dl>
    <dt>
      <a>
        <xsl:attribute name="name">
          <xsl:call-template name="object.id"/>
        </xsl:attribute>
      </a>
      <span class="abstract-heading">
        <xsl:apply-templates select="." mode="object.title.markup"/>
        <xsl:text>:</xsl:text>
      </span>
    </dt>
    <dd>
      <xsl:apply-templates mode="titlepage.mode"/>
    </dd>
  </dl>
</xsl:template>

<xsl:template match="legalnotice[title]" mode="titlepage.mode">
  <dl>
    <dt>
      <a>
        <xsl:attribute name="name">
          <xsl:call-template name="object.id"/>
        </xsl:attribute>
      </a>
      <span class="status-heading">
        <xsl:apply-templates select="." mode="object.title.markup"/>
        <xsl:text>:</xsl:text>
      </span>
    </dt>
    <dd>
      <xsl:apply-templates mode="titlepage.mode"/>
    </dd>
  </dl>
</xsl:template>

<xsl:template match="legalnotice/title" mode="titlepage.mode">
  <!-- nop -->
</xsl:template>

<xsl:template match="legalnotice[@role='notices']" mode="titlepage.mode"
              priority="1">
  <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>

<xsl:template match="legalnotice[@role='notices']/title" mode="titlepage.mode"
              priority="1">
  <hr/>
  <h2 class="notices-heading">
    <xsl:apply-templates/>
  </h2>
</xsl:template>

<xsl:template match="releaseinfo" mode="titlepage.mode">
  <xsl:comment>
    <xsl:text> </xsl:text>
    <xsl:apply-templates/>
    <xsl:text> </xsl:text>
  </xsl:comment>
</xsl:template>

<xsl:template match="jobtitle|shortaffil|orgname|contrib"
              mode="titlepage.mode">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="phrase[@role='keyword']//text()">
  <xsl:value-of select="translate(.,'&lower;','&upper;')"/>
</xsl:template>

<!-- ============================================================ -->
<!-- Component TOC -->

<xsl:template name="component.toc">
  <xsl:if test="$generate.component.toc != 0">
    <xsl:variable name="nodes" select="section|sect1"/>
    <xsl:variable name="apps" select="bibliography|glossary|appendix"/>

    <xsl:if test="$nodes">
      <div class="toc">
        <h2>
          <xsl:call-template name="gentext">
            <xsl:with-param name="key">TableofContents</xsl:with-param>
          </xsl:call-template>
        </h2>

        <xsl:if test="$nodes">
          <xsl:element name="{$toc.list.type}">
            <xsl:apply-templates select="$nodes" mode="toc"/>
          </xsl:element>
        </xsl:if>

        <xsl:if test="$apps">
          <h3>Appendixes</h3>

          <xsl:element name="{$toc.list.type}">
            <xsl:apply-templates select="$apps" mode="toc"/>
          </xsl:element>
        </xsl:if>
      </div>
      <hr/>
    </xsl:if>
  </xsl:if>
</xsl:template>

<xsl:template match="appendix" mode="object.title.template">
  <xsl:text>Appendix </xsl:text>
  <xsl:apply-imports/>
</xsl:template>

<!-- ================================================================= -->

<!-- support role='non-normative' -->
<xsl:template match="preface|chapter|appendix" mode="title.markup">
  <xsl:param name="allow-anchors" select="'0'"/>
  <xsl:variable name="title" select="(docinfo/title
                                      |prefaceinfo/title
                                      |chapterinfo/title
                                      |appendixinfo/title
                                      |title)[1]"/>
  <xsl:if test="@role='iso-normative'">
    <xsl:text>(normative) </xsl:text>
  </xsl:if>
  <xsl:if test="@role='iso-informative'">
    <xsl:text>(informative) </xsl:text>
  </xsl:if>
  <xsl:apply-templates select="$title" mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
  <xsl:if test="@role='non-normative'">
    <xsl:text> (Non-Normative)</xsl:text>
  </xsl:if>
  <xsl:if test="@role='normative'">
    <xsl:text> (Normative)</xsl:text>
  </xsl:if>
  <xsl:if test="@role='informative'">
    <xsl:text> (Informative)</xsl:text>
  </xsl:if>
</xsl:template>

<!-- support role='non-normative' -->
<xsl:template match="section
                     |sect1|sect2|sect3|sect4|sect5
                     |refsect1|refsect2|refsect3
                     |simplesect"
              mode="title.markup">
  <xsl:param name="allow-anchors" select="'0'"/>
  <xsl:variable name="title" select="(sectioninfo/title
                                      |sect1info/title
                                      |sect2info/title
                                      |sect3info/title
                                      |sect4info/title
                                      |sect5info/title
                                      |refsect1info/title
                                      |refsect2info/title
                                      |refsect3info/title
                                      |title)[1]"/>
  <xsl:if test="@role='iso-normative'">
    <xsl:text>(normative) </xsl:text>
  </xsl:if>
  <xsl:if test="@role='iso-informative'">
    <xsl:text>(informative) </xsl:text>
  </xsl:if>
  <xsl:apply-templates select="$title" mode="title.markup">
    <xsl:with-param name="allow-anchors" select="$allow-anchors"/>
  </xsl:apply-templates>
  <xsl:if test="@role='non-normative'">
    <xsl:text> (Non-Normative)</xsl:text>
  </xsl:if>
  <xsl:if test="@role='normative'">
    <xsl:text> (Normative)</xsl:text>
  </xsl:if>
  <xsl:if test="@role='informative'">
    <xsl:text> (Informative)</xsl:text>
  </xsl:if>
</xsl:template>

<!-- ============================================================ -->
<!-- Formatting changes for OASIS look&amp;feel -->

<xsl:template match="quote">
  <xsl:variable name="depth">
    <xsl:call-template name="dot.count">
      <xsl:with-param name="string">
        <xsl:number level="multiple"/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:variable>
  <xsl:choose>
    <xsl:when test="$depth mod 2 = 0">
      <xsl:text>"</xsl:text>
      <xsl:call-template name="inline.charseq"/>
      <xsl:text>"</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>'</xsl:text>
      <xsl:call-template name="inline.charseq"/>
      <xsl:text>'</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="filename">
  <b>
    <xsl:apply-templates/>
  </b>
</xsl:template>

<!--the entire template has to change just to get italics and no bold-->
<xsl:template name="formal.object.heading">
  <xsl:param name="object" select="."/>
  <xsl:param name="title">
    <xsl:apply-templates select="$object" mode="object.title.markup">
      <xsl:with-param name="allow-anchors" select="1"/>
    </xsl:apply-templates>
  </xsl:param>

  <p class="title">
    <i>
      <xsl:copy-of select="$title"/>
    </i>
  </p>
</xsl:template>

<!-- ============================================================ -->

<xsl:template match="para/revhistory">
  <xsl:variable name="numcols">
    <xsl:choose>
      <xsl:when test="//authorinitials">3</xsl:when>
      <xsl:otherwise>2</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <div class="{name(.)}">
    <table border="1" width="100%" summary="Revision history">
      <xsl:apply-templates mode="titlepage.mode">
        <xsl:with-param name="numcols" select="$numcols"/>
      </xsl:apply-templates>
    </table>
  </div>
</xsl:template>

<xsl:template match="section[bibliography]/para">
  <!--suppress the paragraphs in the references, per OASIS layout-->
  <xsl:if test="normalize-space(.)">
    <xsl:message>
      <xsl:text>Warning: non-empty bibliographic paragraphs are </xsl:text>
      <xsl:text>ignored in order to meet OASIS layout requirements.</xsl:text>
    </xsl:message>
  </xsl:if>
</xsl:template>

<xsl:template match="bibliolist">
  <xsl:apply-templates select="bibliomixed"/>
  <xsl:if test="*[not(self::bibliomixed)][normalize-space()]">
    <xsl:message>
    <xsl:text>Warning: non-empty non-bibliomixed children of </xsl:text>
    <xsl:text>bibliography elements are </xsl:text>
    <xsl:text>ignored in order to meet OASIS layout requirements.</xsl:text>
    </xsl:message>
  </xsl:if>
</xsl:template>
  
<xsl:template match="bibliomixed/abbrev">
  <b><xsl:apply-imports/></b>
</xsl:template>

<xsl:template match="title" mode="bibliomixed.mode">
  <i><xsl:apply-templates/></i>
</xsl:template>

<xsl:template match="table[starts-with(@role,'font-size-')]//td/node() |
                     table[starts-with(@role,'font-size-')]//entry/node()">
  <span style="font-size:{substring-after(ancestor::table[@role][1]/@role,
                                          'font-size-')}">
    <xsl:apply-imports/>
  </span>
</xsl:template>

<xsl:template match="programlisting[starts-with(@role,'font-size-')]//text()">
  <span style="font-size:{
      substring-after(ancestor::programlisting[@role][1]/@role,'font-size-')}">
    <xsl:apply-imports/>
  </span>
</xsl:template>

<xsl:template name="user.footer.content">
  <table style="font-size:80%" width="100%">
    <tr>
      <td>
        <xsl:value-of select="/*/articleinfo/productname"/>
        <xsl:for-each select="/*/articleinfo/productnumber">
          <xsl:text/>-<xsl:value-of select="."/>
        </xsl:for-each>
        <br/>
        <xsl:value-of select="/*/articleinfo/releaseinfo[@role='track']"/>
      </td>
      <td style="text-align:center">
        <br/>
        <xsl:text>Copyright &#xa9; </xsl:text>
        <xsl:choose>
          <xsl:when test="/*/articleinfo/copyrightyear/holder">
            <xsl:for-each select="/*/articleinfo/copyrightyear/holder">
              <xsl:if test="position()>1">, </xsl:if>
              <xsl:value-of select="."/>
            </xsl:for-each>
          </xsl:when>
          <xsl:otherwise>
            <xsl:text>OASIS Open</xsl:text>
          </xsl:otherwise>
        </xsl:choose>
        <xsl:text> </xsl:text>
        <xsl:value-of select="/*/articleinfo/copyright/year"/>
        <xsl:text>. All Rights Reserved.</xsl:text>
      </td>
      <td style="text-align:right">
        <xsl:value-of select="/*/articleinfo/pubdate"/>
      </td>
    </tr>
  </table>
  
</xsl:template>

<!-- ============================================================ -->

<xsl:template match="processing-instruction('lb')">
  <br/>
</xsl:template>

<!-- ============================================================ -->
<!--Localization-->
<!--Problems have been reported regarding NBSP characters being visible in
    Internet Explorer when dynamically creating the HTML from XML in the
    browser.  All of these localization strings originally had an NBSP
    and now use the separator defined above.  Also, punctuation has been
    modified as required.-->

<xsl:param name="local.l10n.xml" select="document('')"/>
<l:i18n xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0">
  <l:l10n language="en">
   <l:context name="title">
      <l:template name="abstract" text="%t"/>
      <l:template name="answer" text="%t"/>
      <l:template name="appendix" text="Appendix&separator;%n.&separator;%t"/>
      <l:template name="article" text="%t"/>
      <l:template name="authorblurb" text="%t"/>
      <l:template name="bibliodiv" text="%t"/>
      <l:template name="biblioentry" text="%t"/>
      <l:template name="bibliography" text="%t"/>
      <l:template name="bibliolist" text="%t"/>
      <l:template name="bibliomixed" text="%t"/>
      <l:template name="bibliomset" text="%t"/>
      <l:template name="biblioset" text="%t"/>
      <l:template name="blockquote" text="%t"/>
      <l:template name="book" text="%t"/>
      <l:template name="calloutlist" text="%t"/>
      <l:template name="caution" text="%t"/>
      <l:template name="chapter" text="Chapter&separator;%n.&separator;%t"/>
      <l:template name="colophon" text="%t"/>
      <l:template name="dedication" text="%t"/>
      <l:template name="equation" text="Equation&separator;%n.&separator;%t"/>
      <l:template name="example" text="Example&separator;%n.&separator;%t"/>
      <l:template name="figure" text="Figure&separator;%n.&separator;%t"/>
      <l:template name="foil" text="%t"/>
      <l:template name="foilgroup" text="%t"/>
      <l:template name="formalpara" text="%t"/>
      <l:template name="glossary" text="%t"/>
      <l:template name="glossdiv" text="%t"/>
      <l:template name="glosslist" text="%t"/>
      <l:template name="glossentry" text="%t"/>
      <l:template name="important" text="%t"/>
      <l:template name="index" text="%t"/>
      <l:template name="indexdiv" text="%t"/>
      <l:template name="itemizedlist" text="%t"/>
      <l:template name="legalnotice" text="%t"/>
      <l:template name="listitem" text=""/>
      <l:template name="lot" text="%t"/>
      <l:template name="msg" text="%t"/>
      <l:template name="msgexplan" text="%t"/>
      <l:template name="msgmain" text="%t"/>
      <l:template name="msgrel" text="%t"/>
      <l:template name="msgset" text="%t"/>
      <l:template name="msgsub" text="%t"/>
      <l:template name="note" text="%t"/>
      <l:template name="orderedlist" text="%t"/>
      <l:template name="part" text="Part&separator;%n.&separator;%t"/>
      <l:template name="partintro" text="%t"/>
      <l:template name="preface" text="%t"/>
      <l:template name="procedure" text="%t"/>
      <l:template name="procedure.formal" text="Procedure&separator;%n.&separator;%t"/>
      <l:template name="productionset" text="%t"/>
      <l:template name="productionset.formal" text="Production&separator;%n"/>
      <l:template name="qandadiv" text="%t"/>
      <l:template name="qandaentry" text="%t"/>
      <l:template name="qandaset" text="%t"/>
      <l:template name="question" text="%t"/>
      <l:template name="refentry" text="%t"/>
      <l:template name="reference" text="%t"/>
      <l:template name="refsection" text="%t"/>
      <l:template name="refsect1" text="%t"/>
      <l:template name="refsect2" text="%t"/>
      <l:template name="refsect3" text="%t"/>
      <l:template name="refsynopsisdiv" text="%t"/>
      <l:template name="refsynopsisdivinfo" text="%t"/>
      <l:template name="segmentedlist" text="%t"/>
      <l:template name="set" text="%t"/>
      <l:template name="setindex" text="%t"/>
      <l:template name="sidebar" text="%t"/>
      <l:template name="step" text="%t"/>
      <l:template name="table" text="Table&separator;%n.&separator;%t"/>
      <l:template name="task" text="%t"/>
      <l:template name="tip" text="%t"/>
      <l:template name="toc" text="%t"/>
      <l:template name="variablelist" text="%t"/>
      <l:template name="varlistentry" text=""/>
      <l:template name="warning" text="%t"/>
   </l:context>

    <l:context name="title-numbered">
      <l:template name="appendix" text="Appendix&separator;%n&separator;%t"/>
      <l:template name="article/appendix" text="%n&separator;%t"/>
      <l:template name="bridgehead" text="%n&separator;%t"/>
      <l:template name="chapter" text="Chapter&separator;%n&separator;%t"/>
      <l:template name="sect1" text="%n&separator;%t"/>
      <l:template name="sect2" text="%n&separator;%t"/>
      <l:template name="sect3" text="%n&separator;%t"/>
      <l:template name="sect4" text="%n&separator;%t"/>
      <l:template name="sect5" text="%n&separator;%t"/>
      <l:template name="section" text="%n&separator;%t"/>
      <l:template name="simplesect" text="%t"/>
      <l:template name="part" text="Part&separator;%n&separator;%t"/>
    </l:context>
    
   <l:context name="xref">
      <l:template name="abstract" text="%t"/>
      <l:template name="answer" text="A:&separator;%n"/>
      <l:template name="appendix" text="%t"/>
      <l:template name="article" text="%t"/>
      <l:template name="authorblurb" text="%t"/>
      <l:template name="bibliodiv" text="%t"/>
      <l:template name="bibliography" text="%t"/>
      <l:template name="bibliomset" text="%t"/>
      <l:template name="biblioset" text="%t"/>
      <l:template name="blockquote" text="%t"/>
      <l:template name="book" text="%t"/>
      <l:template name="calloutlist" text="%t"/>
      <l:template name="caution" text="%t"/>
      <l:template name="chapter" text="%t"/>
      <l:template name="colophon" text="%t"/>
      <l:template name="constraintdef" text="%t"/>
      <l:template name="dedication" text="%t"/>
      <l:template name="equation" text="%t"/>
      <l:template name="example" text="%t"/>
      <l:template name="figure" text="%t"/>
      <l:template name="foil" text="%t"/>
      <l:template name="foilgroup" text="%t"/>
      <l:template name="formalpara" text="%t"/>
      <l:template name="glossary" text="%t"/>
      <l:template name="glossdiv" text="%t"/>
      <l:template name="important" text="%t"/>
      <l:template name="index" text="%t"/>
      <l:template name="indexdiv" text="%t"/>
      <l:template name="itemizedlist" text="%t"/>
      <l:template name="legalnotice" text="%t"/>
      <l:template name="listitem" text="%n"/>
      <l:template name="lot" text="%t"/>
      <l:template name="msg" text="%t"/>
      <l:template name="msgexplan" text="%t"/>
      <l:template name="msgmain" text="%t"/>
      <l:template name="msgrel" text="%t"/>
      <l:template name="msgset" text="%t"/>
      <l:template name="msgsub" text="%t"/>
      <l:template name="note" text="%t"/>
      <l:template name="orderedlist" text="%t"/>
      <l:template name="part" text="%t"/>
      <l:template name="partintro" text="%t"/>
      <l:template name="preface" text="%t"/>
      <l:template name="procedure" text="%t"/>
      <l:template name="productionset" text="%t"/>
      <l:template name="qandadiv" text="%t"/>
      <l:template name="qandaentry" text="Q:&separator;%n"/>
      <l:template name="qandaset" text="%t"/>
      <l:template name="question" text="Q:&separator;%n"/>
      <l:template name="reference" text="%t"/>
      <l:template name="refsynopsisdiv" text="%t"/>
      <l:template name="segmentedlist" text="%t"/>
      <l:template name="set" text="%t"/>
      <l:template name="setindex" text="%t"/>
      <l:template name="sidebar" text="%t"/>
      <l:template name="table" text="%t"/>
      <l:template name="tip" text="%t"/>
      <l:template name="toc" text="%t"/>
      <l:template name="variablelist" text="%t"/>
      <l:template name="varlistentry" text="%n"/>
      <l:template name="warning" text="%t"/>
      <l:template name="olink.document.citation" text=" in %o"/>
      <l:template name="olink.page.citation" text=" (page %p)"/>
      <l:template name="page.citation" text=" [%p]"/>
      <l:template name="page" text="(page %p)"/>
      <l:template name="docname" text=" in %o"/>
      <l:template name="docnamelong" text=" in the document titled %o"/>
      <l:template name="pageabbrev" text="(p. %p)"/>
      <l:template name="Page" text="Page %p"/>
      <l:template name="bridgehead" text="the section called &#8220;%t&#8221;"/>
      <l:template name="refsection" text="the section called &#8220;%t&#8221;"/>
      <l:template name="refsect1" text="the section called &#8220;%t&#8221;"/>
      <l:template name="refsect2" text="the section called &#8220;%t&#8221;"/>
      <l:template name="refsect3" text="the section called &#8220;%t&#8221;"/>
      <l:template name="sect1" text="the section called &#8220;%t&#8221;"/>
      <l:template name="sect2" text="the section called &#8220;%t&#8221;"/>
      <l:template name="sect3" text="the section called &#8220;%t&#8221;"/>
      <l:template name="sect4" text="the section called &#8220;%t&#8221;"/>
      <l:template name="sect5" text="the section called &#8220;%t&#8221;"/>
      <l:template name="section" text="the section called &#8220;%t&#8221;"/>
      <l:template name="simplesect" text="the section called &#8220;%t&#8221;"/>
   </l:context>

   <l:context name="xref-number">
      <l:template name="answer" text="A:&separator;%n"/>
      <l:template name="appendix" text="Appendix&separator;%n"/>
      <l:template name="bridgehead" text="Section&separator;%n"/>
      <l:template name="chapter" text="Chapter&separator;%n"/>
      <l:template name="equation" text="Equation&separator;%n"/>
      <l:template name="example" text="Example&separator;%n"/>
      <l:template name="figure" text="Figure&separator;%n"/>
      <l:template name="part" text="Part&separator;%n"/>
      <l:template name="procedure" text="Procedure&separator;%n"/>
      <l:template name="productionset" text="Production&separator;%n"/>
      <l:template name="qandadiv" text="Q &amp; A&separator;%n"/>
      <l:template name="qandaentry" text="Q:&separator;%n"/>
      <l:template name="question" text="Q:&separator;%n"/>
      <l:template name="sect1" text="Section&separator;%n"/>
      <l:template name="sect2" text="Section&separator;%n"/>
      <l:template name="sect3" text="Section&separator;%n"/>
      <l:template name="sect4" text="Section&separator;%n"/>
      <l:template name="sect5" text="Section&separator;%n"/>
      <l:template name="section" text="Section&separator;%n"/>
      <l:template name="table" text="Table&separator;%n"/>
   </l:context>

   <l:context name="xref-number-and-title">
      <l:template name="appendix" text="Appendix&separator;%n, %t"/>
      <l:template name="bridgehead" text="Section&separator;%n, &#8220;%t&#8221;"/>
      <l:template name="chapter" text="Chapter&separator;%n, %t"/>
      <l:template name="equation" text="Equation&separator;%n, &#8220;%t&#8221;"/>
      <l:template name="example" text="Example&separator;%n, &#8220;%t&#8221;"/>
      <l:template name="figure" text="Figure&separator;%n, &#8220;%t&#8221;"/>
      <l:template name="part" text="Part&separator;%n, &#8220;%t&#8221;"/>
      <l:template name="procedure" text="Procedure&separator;%n, &#8220;%t&#8221;"/>
      <l:template name="productionset" text="Production&separator;%n, &#8220;%t&#8221;"/>
      <l:template name="qandadiv" text="Q &amp; A&separator;%n, &#8220;%t&#8221;"/>
      <l:template name="refsect1" text="the section called &#8220;%t&#8221;"/>
      <l:template name="refsect2" text="the section called &#8220;%t&#8221;"/>
      <l:template name="refsect3" text="the section called &#8220;%t&#8221;"/>
      <l:template name="refsection" text="the section called &#8220;%t&#8221;"/>
      <l:template name="sect1" text="Section&separator;%n, &#8220;%t&#8221;"/>
      <l:template name="sect2" text="Section&separator;%n, &#8220;%t&#8221;"/>
      <l:template name="sect3" text="Section&separator;%n, &#8220;%t&#8221;"/>
      <l:template name="sect4" text="Section&separator;%n, &#8220;%t&#8221;"/>
      <l:template name="sect5" text="Section&separator;%n, &#8220;%t&#8221;"/>
      <l:template name="section" text="Section&separator;%n, &#8220;%t&#8221;"/>
      <l:template name="simplesect" text="the section called &#8220;%t&#8221;"/>
      <l:template name="table" text="Table&separator;%n, &#8220;%t&#8221;"/>
   </l:context>

  </l:l10n>
</l:i18n>
<xsl:param name="autotoc.label.separator" select="'&separator;'"/>

</xsl:stylesheet>
