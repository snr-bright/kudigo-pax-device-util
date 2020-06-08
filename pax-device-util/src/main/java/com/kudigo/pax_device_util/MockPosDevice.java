package com.kudigo.pax_device_util;


import com.interswitchng.smartpos.shared.interfaces.device.DevicePrinter;
import com.interswitchng.smartpos.shared.interfaces.device.EmvCardReader;
import com.interswitchng.smartpos.shared.interfaces.device.POSDevice;
import com.interswitchng.smartpos.shared.models.core.TerminalInfo;
import com.interswitchng.smartpos.shared.models.core.UserType;
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject;
import com.interswitchng.smartpos.shared.models.printer.info.PrintStatus;
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvMessage;
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvResult;
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.EmvData;
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.response.TransactionResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.Channel;


public class MockPosDevice implements POSDevice {

    @Override
    public DevicePrinter getPrinter() {
        return new DevicePrinter() {
            @Override
            public PrintStatus printSlip(List<? extends PrintObject> slip, UserType user) {
                return new PrintStatus.Error("No DevicePrinterImpl installed");
            }

            @Override
            public PrintStatus canPrint() {
                return new PrintStatus.Error("No DevicePrinterImpl installed");
            }
        };
    }


    @Override
    public EmvCardReader getEmvCardReader() {
        return new EmvCardReader() {

            @Nullable
            @Override
            public Object setupTransaction(int i, @NotNull TerminalInfo terminalInfo, @NotNull Channel<EmvMessage> channel, @NotNull CoroutineScope coroutineScope, @NotNull Continuation<? super Unit> continuation) {
                return null;
            }

            @Override
            public EmvResult completeTransaction(TransactionResponse response) {
                return EmvResult.OFFLINE_APPROVED;
            }

            @Override
            public EmvResult startTransaction() {
                return EmvResult.OFFLINE_APPROVED;
            }

            @Override
            public void cancelTransaction() {
            }

            @Override
            public EmvData getTransactionInfo() {
                return null;
            }
        };
    }

}