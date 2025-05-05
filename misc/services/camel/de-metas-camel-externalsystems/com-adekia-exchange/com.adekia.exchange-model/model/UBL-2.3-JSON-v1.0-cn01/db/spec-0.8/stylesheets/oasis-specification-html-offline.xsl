<?xml version="1.0"?>
<!DOCTYPE xsl:stylesheet
[
  <!ENTITY logo-uri           'OASISLogo.png'>   
  <!ENTITY logo     SYSTEM '../OASISLogo.png' NDATA dummy>
  <!ENTITY css-uri            'css/'>   
  <!ENTITY css      SYSTEM '../css/' NDATA dummy>
  <!NOTATION dummy  SYSTEM "">
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">
<!-- $Id: oasis-specification-html-offline.xsl,v 1.6 2019/02/25 02:22:57 admin Exp $ -->

<!-- Imported stylesheet is a customization of the DocBook XSL Stylesheets -->
<!-- from http://docs.oasis-open.org/templates/
     to have resource locations parameterized -->
<!-- See http://sourceforge.net/projects/docbook/ -->
<xsl:import href="oasis-specification-html.xsl"/>

<!-- ============================================================ -->
<!-- Parameters -->

<xsl:param name="css.path">
  <xsl:choose>
    <xsl:when test="/processing-instruction('oasis-spec-base-uri')">
      <xsl:value-of
               select="/processing-instruction('oasis-spec-base-uri')"/>
      <xsl:text>&css-uri;</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:for-each select="document('')">
        <xsl:value-of select="unparsed-entity-uri('css')"/>
      </xsl:for-each>
    </xsl:otherwise>
  </xsl:choose>
</xsl:param>

<xsl:param name="oasis.logo">
  <xsl:choose>
    <xsl:when test="/processing-instruction('oasis-spec-base-uri')">
      <xsl:value-of
               select="/processing-instruction('oasis-spec-base-uri')"/>
      <xsl:text>&logo-uri;</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:for-each select="document('')">
        <xsl:value-of select="unparsed-entity-uri('logo')"/>
      </xsl:for-each>
    </xsl:otherwise>
  </xsl:choose>
</xsl:param>

<xsl:param name="oasis-base" select="'no'"/>

</xsl:stylesheet>
