package org.shivang.banking.controller;

import lombok.RequiredArgsConstructor;
import org.shivang.banking.model.Transfer;
import org.shivang.banking.model.dtos.TransferRequest;
import org.shivang.banking.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
