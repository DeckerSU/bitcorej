/*************************************************************************************
 * Copyright (C) 2018 GENERAL BYTES s.r.o. All rights reserved.
 *
 * This software may be distributed and modified under the terms of the GNU
 * General Public License version 2 (GPL2) as published by the Free Software
 * Foundation and appearing in the file GPL2.TXT included in the packaging of
 * this file. Please note that GPL2 Section 2[b] requires that all works based
 * on this software must also be made publicly available under the terms of
 * the GPL2 ("Copyleft").
 *
 * Contact information
 * -------------------
 *
 * GENERAL BYTES s.r.o.
 * Web      :  http://www.generalbytes.com
 *
 ************************************************************************************/
package org.bitcorej.chain.xmr.core.key;

import org.bitcorej.chain.xmr.core.Utils;

import java.util.Arrays;

public class PublicKey implements Key {
    private final byte[] bytes;

    PublicKey(byte[] bytes)
    {
        this.bytes = bytes;
    }

    public byte[] getEncoded()
    {
        return Arrays.copyOf(this.bytes, this.bytes.length);
    }

    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        PublicKey publicKey = (PublicKey)o;

        return Arrays.equals(this.bytes, publicKey.bytes);
    }

    public int hashCode()
    {
        return Arrays.hashCode(this.bytes);
    }

    @Override
    public String toString() {
        return Utils.bytesToHex(bytes);
    }

}
