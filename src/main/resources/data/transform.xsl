<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes" encoding="UTF-8" />
    <xsl:strip-space elements="*" />

    <xsl:key name="planeType" match="Plane" use="Chars/Type" />

    <xsl:template match="/Planes">
        <Planes>
            <xsl:for-each
                select="Plane[generate-id() = generate-id(key('planeType', Chars/Type)[1])]">
                <xsl:sort select="Chars/Type" />
                <xsl:element name="{Chars/Type}">
                    <xsl:apply-templates select="key('planeType', Chars/Type)">
                        <xsl:sort select="Price" data-type="number" />
                    </xsl:apply-templates>
                </xsl:element>
            </xsl:for-each>
        </Planes>
    </xsl:template>

    <xsl:template match="*">
        <xsl:element name="{local-name()}">
            <xsl:copy-of select="@*" />
            <xsl:apply-templates />
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>