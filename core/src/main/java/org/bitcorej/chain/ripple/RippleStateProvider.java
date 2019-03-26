package org.bitcorej.chain.ripple;

import com.ripple.core.coretypes.AccountID;
import com.ripple.core.coretypes.Amount;
import com.ripple.core.coretypes.uint.UInt32;
import com.ripple.core.types.known.tx.signed.SignedTransaction;
import com.ripple.core.types.known.tx.txns.Payment;
import org.bitcorej.chain.ChainState;
import org.bitcorej.chain.KeyPair;
import org.bitcorej.chain.Transaction;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

public class RippleStateProvider implements ChainState {
    private static final BigDecimal DECIMALS = new BigDecimal(10).pow(6);

    private static final BigDecimal MAX_FEE = new BigDecimal("50").multiply(DECIMALS);

//    private String createAddress(PublicKey pubKey) {
//        byte[] pubKeyData = pubKey.getRaw();
//
//        SHA256Digest pubKeyInnerHash = new SHA256Digest();
//        pubKeyInnerHash.update(pubKeyData, 0, pubKeyData.length);
//        byte[] pubKeyInnerBytes = new byte[pubKeyInnerHash.getDigestSize()];
//        pubKeyInnerHash.doFinal(pubKeyInnerBytes, 0);
//
//        RIPEMD160Digest pubKeyOuterHash = new RIPEMD160Digest();
//        pubKeyOuterHash.update(pubKeyInnerBytes, 0, pubKeyInnerHash.getDigestSize());
//        byte[] accountId = new byte[pubKeyOuterHash.getDigestSize()];
//        pubKeyOuterHash.doFinal(accountId, 0);
//
//        byte[] addressTypePrefix = NumericUtil.hexToBytes("0x00");
//        byte[] payload = ByteUtil.concat(addressTypePrefix, accountId);
//        SHA256Digest checksum1 = new SHA256Digest();
//        checksum1.update(payload, 0, payload.length);
//        byte[] checksum1Bytes = new byte[checksum1.getDigestSize()];
//        checksum1.doFinal(checksum1Bytes, 0);
//
//        SHA256Digest checksum2 = new SHA256Digest();
//        checksum2.update(checksum1Bytes, 0, checksum1Bytes.length);
//        byte[] out = new byte[checksum2.getDigestSize()];
//        checksum2.doFinal(out, 0);
//        byte[] checksum2Bytes = Arrays.copyOfRange(out, 0, 4);
//        return Base58.encode(ByteUtil.concat(payload, checksum2Bytes));
//    }

    @Override
    public KeyPair generateKeyPair(String secret) {
        return null;
    }

    @Override
    public KeyPair generateKeyPair() {
        return null;
    }

    @Override
    public Boolean validateTx(String rawTx, String tx) {
        Transaction decodedTx = this.decodeRawTransaction(rawTx);
        return decodedTx.equals(new Transaction(tx)) && decodedTx.getFee().compareTo(MAX_FEE) < 0;
    }

    @Override
    public Transaction decodeRawTransaction(String rawTx) {
        JSONObject jsonObject = new JSONObject(rawTx);

        Transaction tx = new Transaction();
        JSONObject amount = jsonObject.getJSONObject("Amount");
        tx.addInput(tx.new Input(jsonObject.getString("Account"), new BigDecimal(amount.getString("value"))));
        tx.addOutput(tx.new Output(jsonObject.getString("Destination"), new BigDecimal(amount.getString("value")), jsonObject.getString("DestinationTag")));
        tx.setFee(new BigDecimal(jsonObject.getString("Fee")).divide(DECIMALS));
        return tx;
    }

    @Override
    public String signRawTransaction(String rawTx, List<String> keys) {
        Payment payment = new Payment();

        JSONObject jsonObject = new JSONObject(rawTx);

        payment.as(AccountID.Account, jsonObject.getString("Account"));
        payment.as(AccountID.Destination, jsonObject.getString("Destination"));
        payment.as(UInt32.DestinationTag, jsonObject.getString("DestinationTag"));
        payment.as(Amount.Amount, jsonObject.getJSONObject("Amount").getString("value"));
        payment.as(UInt32.Sequence, jsonObject.getInt("Sequence"));
        payment.as(UInt32.LastLedgerSequence, jsonObject.getInt("LastLedgerSequence"));
        payment.as(Amount.Fee, jsonObject.getString("Fee"));
        SignedTransaction signed = payment.sign(keys.get(0));
        if (signed != null) {
            JSONObject packedTx = new JSONObject();
            packedTx.put("hex", signed.tx_blob);
            return packedTx.toString();
        }
        return null;
    }
}