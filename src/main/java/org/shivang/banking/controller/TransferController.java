package org.shivang.banking.controller;

import lombok.RequiredArgsConstructor;
import org.shivang.banking.model.Transfer;
import org.shivang.banking.model.dtos.TransferRequest;
import org.shivang.banking.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfer")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;
    @PostMapping
    public ResponseEntity<?> transferMoney(@RequestBody TransferRequest transferRequest){
            Transfer transfer = transferService.transferMoney(transferRequest);
            return ResponseEntity.status(HttpStatus.OK).body(transfer);


    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<Transfer>> getAllTransferHistory(@PathVariable String accountNumber){
        List<Transfer> transfers = transferService.getTransfersByAccount(accountNumber);
        return ResponseEntity.status(HttpStatus.OK).body(transfers);
    }

}
