/*
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cose.structure;

import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.Map;
import com.google.cose.utils.CborUtils;
import com.google.cose.utils.CoseUtils;

/**
 * Encodes the MAC_Structure as mentioned in COSE RFC section 6.3
 */
public class MacStructure {

  /**
   * Context strings for MacStructure.
   */
  public enum MacContext {
    MAC0("MAC0"),
    MAC("MAC");

    private final String context;

    MacContext(String context) {
      this.context = context;
    }

    public String getContext() {
      return this.context;
    }
  }

  private final MacContext context;
  private final Map protectedHeaders;
  private final byte[] externalAad;
  private final byte[] message;

  public MacStructure(MacContext context, Map headers, byte[] externalAad, byte[] message) {
    this.context = context;
    this.protectedHeaders = headers;
    this.externalAad = (externalAad != null) ? externalAad : new byte[0];
    this.message = (message != null) ? message : new byte[0];
  }

  public byte[] serialize() throws CborException {
    return CborUtils.encode(encode());
  }

  public DataItem encode() throws CborException {
    return CoseUtils.encodeStructure(context.getContext(), protectedHeaders, null, externalAad,
        message);
  }
}
