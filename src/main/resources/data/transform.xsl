<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes" omit-xml-declaration="no" />

    <xsl:key name="planeType" match="Plane" use="Chars/Type" />

    <xsl:template match="/Planes">
        <Planes>
            <xsl:for-each
                select="Plane[generate-id() = generate-id(key('planeType', Chars/Type)[1])]">
                <xsl:element name="{Chars/Type}">
                    <xsl:for-each select="key('planeType', Chars/Type)">
                        <xsl:copy-of select="." />
                    </xsl:for-each>
                </xsl:element>
            </xsl:for-each>
        </Planes>
    </xsl:template>
</xsl:stylesheet>