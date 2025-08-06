package com.example.bankcards.controller;

import com.example.bankcards.TestDataBuilder;
import com.example.bankcards.dto.card.CardDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WithMockUser(username = "Ivan", roles = "USER")
public class UserCardControllerTest extends BaseControllerTest {


    private final static String BASE_URI = "/user/cards";

    @Test
    @DisplayName("GET /user/cards — успешный ответ со страницей карт")
    void whenGetMyCards_thenReturnPage() throws Exception {

        PageImpl<CardDto> page = new PageImpl<>(TestDataBuilder.listCardTestData());

        when(cardService.myCards(eq("Ivan"), eq(""), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/user/cards")
                        .param("numberFilter", "")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].number").value("1111-2222-3333-4444"))
                .andExpect(jsonPath("$.content[0].balance").value(100))
                .andExpect(jsonPath("$.content[1].number").value("2222-3333-4444-5555"))
                .andExpect(jsonPath("$.content[1].balance").value(200));

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        then(cardService).should().myCards(eq("Ivan"), eq(""), captor.capture());
    }

    @Test
    @DisplayName("POST /user/cards/{id}/block-request — запрос блокировки")
    void whenPostBlockRequest_thenNoContent() throws Exception {
        mockMvc.perform(post(BASE_URI + "/42/block-request"))
                .andExpect(status().isNoContent());

        then(cardService).should().blockCard("Ivan", 42L);
    }

    @Test
    @DisplayName("GET /user/cards/{id}/balance — вернуть баланс")
    void whenGetBalance_thenReturnAmount() throws Exception {
        when(cardService.balance("Ivan", 99L))
                .thenReturn(BigDecimal.valueOf(1234.56));

        mockMvc.perform(get(BASE_URI + "/99/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("1234.56"));

        then(cardService).should().balance("Ivan", 99L);
    }
}
