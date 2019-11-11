package org.bitcorej.chain;

import org.bitcorej.chain.bitcoin.Recipient;
import org.bitcorej.chain.bitcoin.UnspentOutput;

import java.math.BigDecimal;
import java.util.List;

public interface UTXOState {
    String generateP2PKHScript(String address);

    String encodeTransaction(List<UnspentOutput> utxos, List<Recipient> recipients, String changeAddress, BigDecimal fee);
    String encodeTransaction(List<UnspentOutput> utxos, List<Recipient> recipients, String changeAddress, BigDecimal fee, BigDecimal decimals);
}