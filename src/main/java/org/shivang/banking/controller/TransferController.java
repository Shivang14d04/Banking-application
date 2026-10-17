package org.shivang.banking.controller;

import lombok.RequiredArgsConstructor;
import org.shivang.banking.model.dtos.TransferRequest;
import org.shivang.banking.model.dtos.TransferResponse;
import org.shivang.banking.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/transfer")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;
    @PostMapping
    public ResponseEntity<TransferResponse> transferMoney(@Valid @RequestBody TransferRequest transferRequest){
            TransferResponse transfer = transferService.transferMoney(transferRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(transfer);

    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<TransferResponse>> getAllTransferHistory(@PathVariable String accountNumber){
        List<TransferResponse> transfers = transferService.getTransfersByAccount(accountNumber);
        return ResponseEntity.status(HttpStatus.OK).body(transfers);
    }

}
