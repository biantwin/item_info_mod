# 物品信息显示模组 (Item Info Mod)

一个用于在Minecraft中显示物品详细信息的Forge模组，包括食物信息、NBT数据、物品名称和耐久度等。

## 功能特性

- 显示手持物品的食物信息（饱和度、营养值等）
- 显示物品的NBT数据
- 显示非食物物品的完整名称
- 显示工具和武器的耐久度信息
- 支持通过按键快速查看物品信息
- 可通过命令行动态开启/关闭各项功能

## 安装要求

- Minecraft 1.20.1
- Forge 47.4.13 或更高版本

## 使用方法

### 按键操作

- 默认按键：**B键** - 按下B键显示当前手持物品的详细信息

> 注意：可以在游戏内的"选项" -> "控制" -> "其他"中修改按键绑定

### 游戏内命令

使用以下命令来控制模组的不同功能：

#### 主命令
```
/iteminfo
```
- 显示当前各项功能的启用状态

#### 控制物品信息
```
/iteminfo item true      # 启用物品信息显示
/iteminfo item false     # 禁用物品信息显示
```

#### 控制食物信息
```
/iteminfo food true      # 启用食物信息显示
/iteminfo food false     # 禁用食物信息显示
```

#### 控制NBT信息
```
/iteminfo nbt true       # 启用NBT信息显示
/iteminfo nbt false      # 禁用NBT信息显示
```

#### 控制物品名称
```
/iteminfo name true      # 启用物品名称显示
/iteminfo name false     # 禁用物品名称显示
```

#### 控制耐久度信息
```
/iteminfo durability true   # 启用耐久度信息显示
/iteminfo durability false  # 禁用耐久度信息显示
```

## 配置文件

模组会在配置目录中生成 `item_info_mod-client.toml` 文件，您可以手动编辑此文件来配置各项功能：

```toml
# 是否启用物品信息功能（总开关）
enableItemInfo = true

# 是否显示食物信息
enableFoodInfo = true

# 是否显示NBT数据
enableNBTInfo = false

# 是否显示物品名称
enableItemName = true

# 是否显示耐久度信息
enableDurabilityInfo = true
```

## 信息显示格式

- **食物信息**：显示营养价值、饱和度等信息
- **NBT信息**：显示物品的原始NBT数据
- **物品名称**：显示物品的完整内部名称
- **耐久度信息**：显示工具/武器/护甲的当前耐久度和最大耐久度

## 故障排除

### 按键不起作用
1. 检查是否在游戏设置中重新绑定了按键
2. 确认没有与其他模组的按键冲突

### 信息不显示
1. 检查对应功能是否已启用：`/iteminfo`
2. 使用命令启用对应功能，如 `/iteminfo item true`

### 游戏崩溃
1. 检查Forge版本是否符合要求
2. 查看游戏日志以获取更多错误信息

## 更新日志

### 版本 1.0.1
- 修复了配置加载时可能导致的崩溃问题
- 增加了耐久度信息显示功能
- 优化了配置访问的安全性

### 早期版本
- 初始发布版本
- 包含基本的物品信息、食物信息、NBT数据显示功能

## 致谢

感谢所有测试人员和反馈用户对本模组的支持！