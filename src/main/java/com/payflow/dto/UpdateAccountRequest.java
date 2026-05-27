package com.payflow.dto;

import com.payflow.entity.Account;
import lombok.Data;

@Data
public class UpdateAccountRequest {
    private Account.AccountType accountType;
}